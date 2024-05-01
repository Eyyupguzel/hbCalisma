package helpers.api;

import com.google.gson.*;
import helpers.base.IniHelper;
import helpers.base.Logger;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.util.*;
import static io.restassured.RestAssured.given;


public class ApiHelper {
    public org.apache.log4j.Logger logger = Logger.getLogger(Logger.class);
    private final IniHelper iniHelper;
    private String apiUrl;
    private RequestSpecification request;
    private String requestBody;
    private JsonObject requestBodyJson;
    private ValidatableResponse validatableResponse;
    private Response unvalidatibleResponse;
    private JsonPath jsonResponse;
    private String stringResponse;
    private Map mapResponse;
    private Map<String, String> headers;
    private Map<String, Object> queryParams;
    private Object responseValue;
    private int statusCode;


    public ApiHelper(IniHelper iniHelper) throws Exception {
        this.iniHelper = iniHelper;
        if (request == null) {
            request = prepareForNewRequest();
        }
    }

    /**
     * Bu method ile request temizlenir.
     */
    public RequestSpecification prepareForNewRequest() throws Exception {
        try {
            startPrepareHeaders();
            request = given().contentType(ContentType.JSON);
            logger.info("Request hazırlandııı");
        } catch (Exception e) {
            logger.error("The method causing the error : ApiHelper - prepareForNewRequest");
            throw new Exception(e.getMessage());
        }
        return request;
    }

    public void startPrepareHeaders() throws Exception {
        try {
            headers = new HashMap<>();
        } catch (Exception e) {
            logger.error("The method causing the error : ApiHelper - startPrepareHeaders");
            throw new Exception(e.getMessage());
        }
    }



    public void startPrepareRequestBody() throws Exception {
        try {

            requestBodyJson = new JsonObject();
        } catch (Exception e) {
            logger.error("The method causing the error : ApiHelper - cleanRequest");
            throw new Exception(e.getMessage());
        }
    }

    public void addToRequestBodyKey(String key, Object value) {
        requestBodyJson.add(key, getValueAsJsonElement(value));
    }

    public void addToRequestBodyList(String key, Object value) {
        if (!requestBodyJson.has(key)) {
            requestBodyJson.add(key, new JsonArray());
        }
        JsonArray weightsList = requestBodyJson.getAsJsonArray(key);
        if (value.toString().contains(",")) {
            String[] valueParts = value.toString().split(",");

            for (String val : valueParts) {
                weightsList.add(getValueAsJsonElement(val));
            }
        } else {
            weightsList.add(getValueAsJsonElement(value));
        }
    }


    public JsonElement getValueAsJsonElement(Object value) {
        if (value instanceof String) {
            String stringValue = value.toString();
            try {
                // Check if the string value can be parsed as an integer
                int intValue = Integer.parseInt(stringValue);
                return new JsonPrimitive(intValue);
            } catch (NumberFormatException e) {
                String lowercaseValue = stringValue.toLowerCase();
                if (lowercaseValue.equals("true") || lowercaseValue.equals("false")) {
                    return new JsonPrimitive(Boolean.parseBoolean(lowercaseValue));
                }
                return new JsonPrimitive(stringValue);
            }
        } else if (value instanceof Integer) {
            // If the value is already an Integer, return it directly
            return new JsonPrimitive((Integer) value);
        } else if (value instanceof Boolean) {
            // If the value is a Boolean, return it directly
            return new JsonPrimitive((Boolean) value);
        } else if (value instanceof Long) {
            // If the value is a Long, return it directly
            return new JsonPrimitive((Long) value);
        }

        // For other types, return the value as is
        return null;
    }


    public void finishPrepareRequestBody() throws Exception {
        try {
            Gson gson = new Gson();
            requestBody = gson.toJson(requestBodyJson);
            request = request.body(requestBody);
            logger.debug(requestBody);
        } catch (Exception e) {
            throw new Exception("Error occurred while preparing the request body: " + e.getMessage());
        }
    }

    public ValidatableResponse sendRequest(String requestType) throws Exception {
        Assert.assertNotNull(apiUrl);
        Assert.assertNotNull(request);
        request.log().all();
        try {
            switch (requestType) {
                case "get":
                    unvalidatibleResponse = request.body("").get(apiUrl);
                    break;
                case "post":
                    unvalidatibleResponse = request.post(apiUrl);
                    break;
                case "put":
                    unvalidatibleResponse = request.put(apiUrl);
                    break;
                case "delete":
                    unvalidatibleResponse = request.body("").delete(apiUrl);
                    break;
                case "patch":
                    unvalidatibleResponse = request.patch(apiUrl);
                    break;
            }
            Assert.assertNotNull(unvalidatibleResponse);
            unvalidatibleResponse.prettyPrint();
            validatableResponse = unvalidatibleResponse.then();
            statusCode = unvalidatibleResponse.statusCode();

            if (!requestType.equals("delete")) {
                stringResponse = unvalidatibleResponse.asString();
                jsonResponse = new JsonPath(stringResponse);
                mapResponse = validatableResponse.extract().response().getBody().as(Map.class);
                try {
                    saveToDatastoreAllResponseValue();
                } catch (Exception e) {
                    logger.info(e);
                }
            }
            logger.debug("Status Code : " + statusCode);
        } catch (Exception e) {
            logger.error("The method causing the error : ApiHelper - sendRequest");
            throw new Exception(e.getMessage());
        }
        return validatableResponse;
    }


    /**
     * Bu method ile dönen response status code u kontrol edilir
     */
    public boolean verifyStatus(int statusCode) throws Exception {
        try {
            validatableResponse.statusCode(statusCode);
        } catch (Exception e) {
            logger.error("The method causing the error : ApiHelper - verifyStatus");
            throw new Exception(e.getMessage());
        }
        return true;
    }


    /**
     * Bu method ile dönen response içindeki tüm anahtarları dolaşarak değerleri alır
     */
    public void saveToDatastoreAllResponseValue() throws Exception {
        try {
            if (mapResponse.size() > 0) {
                mapResponse = validatableResponse.extract().response().getBody().as(Map.class);
                JSONObject jsonData = new JSONObject(mapResponse);

                for (Object keyName : jsonData.keySet()) {
                    Object value = jsonData.get(keyName);

                    if (value instanceof Map & value != null) {
                        JSONObject innerJsonData = new JSONObject((Map<?, ?>) value);
                        for (Object innerKeyName : innerJsonData.keySet()) {
                            Object innerValue = innerJsonData.get(innerKeyName);
                            if (innerValue instanceof Map & innerValue != null) {
                                JSONObject innerinnerJsonData = new JSONObject((Map<?, ?>) innerValue);
                                for (Object innerinnerKeyName : innerinnerJsonData.keySet()) {
                                    Object innerinnerValue = innerinnerJsonData.get(innerinnerKeyName);
                                    if (innerinnerValue != null) {
                                        iniHelper.writeIniFile("response." + keyName + "." + innerKeyName + "." + innerinnerKeyName, innerinnerValue.toString());
                                    } else {
                                        iniHelper.writeIniFile("response." + keyName + "." + innerKeyName + "." + innerinnerKeyName, "null");
                                    }
                                }
                            } else {
                                if (innerValue != null) {
                                    iniHelper.writeIniFile("response." + keyName + "." + innerKeyName, innerValue.toString());
                                } else {
                                    iniHelper.writeIniFile("response." + keyName + "." + innerKeyName, "null");
                                }
                            }
                        }
                    } else if (value instanceof List) {
                        ArrayList<String> innerList = new ArrayList<>();
                        ArrayList<String> innerInnerList = new ArrayList<>();

                        List<?> listValue = (List<?>) value;
                        for (int i = 0; i < listValue.size(); i++) {
                            Object listItem = listValue.get(i);
                            if (listItem instanceof Map) {
                                JSONObject innerJsonData = new JSONObject((Map<?, ?>) listItem);
                                for (Object innerKeyName : innerJsonData.keySet()) {
                                    Object innerValue = innerJsonData.get(innerKeyName.toString());
                                    innerList.add(innerValue.toString());

                                    if (innerValue instanceof Map) {
                                        JSONObject innerInnerJsonData = new JSONObject((Map<?, ?>) innerValue);

                                        for (Object innerInnerKeyName : innerInnerJsonData.keySet()) {
                                            Object innerInnerValue = innerJsonData.get(innerInnerKeyName.toString());
                                            innerInnerList.add(innerInnerValue.toString());
                                        }
                                        if (innerInnerList != null) {
                                            iniHelper.writeIniFile("response." + keyName + "." + innerKeyName + ".list", String.valueOf(innerInnerList));
                                        } else {
                                            iniHelper.writeIniFile("response." + keyName + "." + innerKeyName + ".list", "null");
                                        }
                                    }
                                }
                                if (innerList != null) {
                                    iniHelper.writeIniFile("response." + keyName + ".list", String.valueOf(innerList));
                                } else {
                                    iniHelper.writeIniFile("response." + keyName + ".list", "null");
                                }
                            } else {
                                if (innerList != null) {
                                    iniHelper.writeIniFile("response." + keyName + ".list", String.valueOf(innerList));
                                } else {
                                    iniHelper.writeIniFile("response." + keyName + ".list", "null");
                                }
                            }
                        }
                    }
                    if (value != null) {
                        iniHelper.writeIniFile("response." + keyName, value.toString());
                    } else {
                        iniHelper.writeIniFile("response." + keyName, "null");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Hata oluşan method: ApiHelper - saveToDatastoreAllResponseValue");
            throw new Exception(e.getMessage());
        }
    }


    /**
     * Bu method ile dönen response içinden key belirtilerek değeri alınır
     */
    public Object getResponseValue(Object key) throws Exception {
        try {
            Assert.assertNotNull(unvalidatibleResponse);
            responseValue = mapResponse.get(String.valueOf(key));
        } catch (Exception e) {
            logger.error("The method causing the error : ApiHelper - getResponseValue");
            throw new Exception(e.getMessage());
        }
        return responseValue;
    }



    public List<Object> getResponseValueses(String key) throws Exception {
        List<Object> responseValues = new ArrayList<>();

        try {
            Assert.assertNotNull(unvalidatibleResponse);

            if (key.contains(".")) {
                // Noktalarla ayrılmış bir anahtar ise
                List<String> keyList = Arrays.asList(key.split("\\."));
                getNestedValues(keyList, mapResponse, responseValues);
            } else {
                // Sadece tek bir anahtar ise
                Object value = mapResponse.get(key);
                if (value != null) {
                    responseValues.add(value);
                }
            }
        } catch (Exception e) {
            logger.error("The method causing the error : ApiHelper - getResponseValues");
            throw new Exception(e.getMessage());
        }
        return responseValues;
    }

    // Listeyi dolaşarak içerideki değerleri alan yardımcı metot
    private void getNestedValues(List<String> keyList, Object value, List<Object> responseValues) {
        for (String key : keyList) {
            if (value instanceof Map) {
                value = ((Map<String, Object>) value).get(key);
            } else if (value instanceof List) {
                // Eğer değer bir liste ise, listenin her bir öğesinde dolaşarak aranan anahtarı bulmaya çalışın
                List<Map<String, Object>> listValue = (List<Map<String, Object>>) value;
                for (Map<String, Object> listItem : listValue) {
                    Object listItemValue = listItem.get(key);
                    if (listItemValue != null) {
                        responseValues.add(listItemValue);
                    }
                }
            } else {
                break;
            }
        }
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String url) throws Exception {
        this.apiUrl = url;
    }

    public RequestSpecification getRequest() {
        return request;
    }

    public void setRequest(RequestSpecification request) {
        this.request = request;
    }

    public String getStringResponse() {
        return stringResponse;
    }



    public String getRequestBody() {
        return requestBody;
    }


}
