package stepdefs.api;


import helpers.api.ApiConfigHelper;
import helpers.api.ApiHelper;
import helpers.base.GherkinHelper;
import helpers.base.IniHelper;
import helpers.base.Logger;
import helpers.project.ProjectHelper;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;


public class ApiBaseStepDefs {
    public org.apache.log4j.Logger logger = Logger.getLogger(Logger.class);
    private final ApiHelper apiHelper;
    private final IniHelper iniHelper;
    private final ProjectHelper projectHelper;
    private final ApiConfigHelper apiConfigHelper;

    public ApiBaseStepDefs(ApiHelper apiHelper, IniHelper iniHelper, ProjectHelper projectHelper,ApiConfigHelper apiConfigHelper) {
        this.apiHelper = apiHelper;
        this.iniHelper = iniHelper;
        this.projectHelper = projectHelper;
        this.apiConfigHelper = apiConfigHelper;

    }

    @Given("start prepare request body")
    public void startPrepareRequestBody() throws Exception {
        apiHelper.startPrepareRequestBody();
    }

    @Given("add request body key {} value {}")
    public void addToRequestBodyKey(String key, Object value) throws Exception {
        key = projectHelper.valuePrepare(key).toString();

        // Değer null ise, boolean olarak ayarla ve null yap
        if (Objects.isNull(value) || "null".equals(String.valueOf(value))) {
            value = null;
        } else {
            value = projectHelper.valuePrepare(value);
        }

        iniHelper.writeIniFile("request." + key, String.valueOf(value));
        apiHelper.addToRequestBodyKey(key, value);
    }

    @Given("add request body list {} value {}")
    public void addToRequestList(String key, Object value) throws Exception {
        key = projectHelper.valuePrepare(key).toString();
        value = projectHelper.valuePrepare(value);
        iniHelper.writeIniFile("request." + key, value.toString());
        apiHelper.addToRequestBodyList(key, value);
    }


    @Given("finish prepare request body")
    public void finishPrepareRequestBody() throws Exception {
        apiHelper.finishPrepareRequestBody();
        logger.debug(apiHelper.getRequestBody());
    }


    /**
     * Belirtilen süre kadar (saniye cinsinden) bekler.
     *
     * @param seconds Beklenecek süre (saniye cinsinden).
     * @throws Exception Bekleme sırasında bir hata oluşursa.
     */
    @And("wait for a {} seconds")
    public void waitForAWhile(int seconds) throws Exception {
        try {
            Thread.sleep(Duration.ofSeconds(seconds).toMillis());
            logger.debug("Step : wait for a " + seconds + " seconds" + " - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : ApiBaseStepDefs - waitForAWhile");
            throw new Exception(e.getMessage());
        }
    }


    /**
     * Belirtilen URL'ye belirtilen türde bir HTTP isteği gönderir.
     *
     * @param requestType İstek türü. Geçerli değerler:
     *                    - "post": POST isteği gönderir.
     *                    - "postWithFormParams": Form parametreleriyle POST isteği gönderir.
     *                    - "putWithFormParams": Form parametreleriyle PUT isteği gönderir.
     *                    - "put": PUT isteği gönderir.
     *                    - "get": GET isteği gönderir.
     *                    - "getWithQueryParams": Sorgu parametreleriyle GET isteği gönderir.
     *                    - "delete": DELETE isteği gönderir.
     * @throws Exception İstek gönderilirken bir hata oluşursa fırlatılır.
     */
    @When("send (" + GherkinHelper.sendRequestCaseList + "){} request to (" + GherkinHelper.apiConfigDataCaseList + "){}")
    public void sendRequest(String requestType, String specifiedUrl) throws Exception {
        try {
            if (!specifiedUrl.startsWith("baseUri")) {
                if (specifiedUrl.endsWith("Url")) {
                    apiHelper.setApiUrl(apiConfigHelper.getApiConfigData(specifiedUrl));
                } else if (specifiedUrl.startsWith("http")) {
                    apiHelper.setApiUrl(specifiedUrl);
                } else if (specifiedUrl.contains("with")) {
                    String configUrl = apiConfigHelper.getApiConfigData(specifiedUrl.split(" ")[0]);
                    String iniUrlData = "/" + iniHelper.getIniFileValue(specifiedUrl.split(" ")[2]);
                    if (specifiedUrl.contains("and")) {
                        iniUrlData = iniUrlData + specifiedUrl.split(" ")[4];
                    }
                    apiHelper.setApiUrl(configUrl + iniUrlData);
                } else {
                    apiHelper.setApiUrl(iniHelper.getIniFileValue(specifiedUrl));
                }
            }
            try {
                apiHelper.sendRequest(requestType);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
            logger.debug("Step : send " + requestType + " request - is success");
        } catch (Exception e) {
            logger.error(apiHelper.getRequest().log().all());
            logger.error("The method causing the error : ApiBaseStepDefs - sendRequest");
            throw new Exception(e.getMessage());
        }
    }


    /**
     * Belirtilen durum kodunu doğrular.
     *
     * @param statusCode Doğrulanacak durum kodu.
     * @return
     * @throws Exception Durum kodu doğrulanamazsa bir hata oluşursa.
     */
    @Then("the status code is {}")
    public boolean verifyStatusCode(int statusCode) throws Exception {
        boolean result;
        try {
            result = apiHelper.verifyStatus(statusCode);
            logger.debug("Step : the status code is " + statusCode + " - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : ApiBaseStepDefs - verifyStatusCode");
            throw new Exception(e.getMessage());
        }
        return result;
    }

    /**
     * Gelen yanıtın belirtilen alanını kontrol eder ve beklenen değerle karşılaştırır.
     *
     * @param key Kontrol edilecek alanın anahtarı.
     * @throws Exception Alan değeri kontrol edilemezse veya senaryo geçerli değilse bir hata oluşursa.
     */
    @And("verify response field {} is null")
    public void responseFieldIsNull(String key) throws Exception {
        try {
            Object currentValue;
            if (key.startsWith("datastore")) {
                currentValue = String.valueOf(iniHelper.getIniFileValue(key.split(" ")[1]));
            } else {
                currentValue = apiHelper.getResponseValue(key);
            }
            if (key.startsWith("datastore")) {
                if (Objects.equals(String.valueOf(currentValue), "null")) {
                    logger.debug("currentValue type : " + currentValue.getClass().getSimpleName() + " currentValue : " + currentValue);
                    logger.error("Step : response field " + key + " is null - is success");
                } else {
                    logger.error("currentValue type : " + currentValue.getClass().getSimpleName() + " currentValue : " + currentValue);
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            logger.error("The method causing the error : ApiBaseStepDefs - responseFieldIs");
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Gelen yanıtın belirtilen alanını kontrol eder ve beklenen değerle karşılaştırır.
     *
     * @param key Kontrol edilecek alanın anahtarı.
     * @throws Exception Alan değeri kontrol edilemezse veya senaryo geçerli değilse bir hata oluşursa.
     */
    @And("verify response field {} is not null")
    public void responseFieldIsNotNull(String key) throws Exception {
        try {
            Object currentValue;
            if (key.startsWith("datastore")) {
                currentValue = iniHelper.getIniFileValue(key.split(" ")[1]);
            } else {
                currentValue = apiHelper.getResponseValue(key);
            }
            logger.debug("currentValue type : " + currentValue.getClass().getSimpleName() + " currentValue : " + currentValue);
            if (key.startsWith("datastore") && Objects.equals(String.valueOf(currentValue), "null")) {
                throw new Exception();
            } else {
                Assert.assertNotNull(currentValue.toString());
            }
            logger.debug("Step : response field " + key + " is not null - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : ApiBaseStepDefs - responseFieldIs");
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Gelen yanıtın belirtilen alanını kontrol eder ve beklenen değerle karşılaştırır.
     *
     * @param key           Kontrol edilecek alanın anahtarı.
     * @param expectedValue Beklenen değer
     * @throws Exception Alan değeri kontrol edilemezse veya senaryo geçerli değilse bir hata oluşursa.
     */
    @And("verify response field {} equals {}")
    public void responseFieldIsEqual(String key, String expectedValue) throws Exception {
        try {
            expectedValue = projectHelper.valuePrepare(expectedValue).toString();
            Object currentValue;
            if (key.startsWith("datastore")) {
                if (Objects.equals(expectedValue, "null")) {
                    currentValue = String.valueOf(iniHelper.getIniFileValue(key.split(" ")[1]));
                } else {
                    currentValue = iniHelper.getIniFileValue(key.split(" ")[1]);
                }
            } else {
                currentValue = apiHelper.getResponseValue(key);
            }

            logger.debug("expectedValue type : " + expectedValue.getClass().getSimpleName() + " expectedValue : " + expectedValue);
            logger.debug("currentValue type : " + currentValue.getClass().getSimpleName() + " currentValue : " + currentValue);
            Assert.assertEquals(expectedValue, currentValue.toString());

            logger.debug("Step : response field " + key + " scenario is equal expected" + expectedValue + " - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : ApiBaseStepDefs - responseFieldIs");
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Gelen yanıtın belirtilen alanını kontrol eder ve beklenen değerle karşılaştırır.
     *
     * @param key           Kontrol edilecek alanın anahtarı.
     * @param expectedValue Beklenen değer
     * @throws Exception Alan değeri kontrol edilemezse veya senaryo geçerli değilse bir hata oluşursa.
     */
    @And("verify response field {} not equals {}")
    public void responseFieldIsNotEqual(String key, String expectedValue) throws Exception {
        Object currentValue = null;
        expectedValue = projectHelper.valuePrepare(key).toString();
        if (key.startsWith("datastore")) {
            currentValue = iniHelper.getIniFileValue(key.split(" ")[1]);
        } else {
            currentValue = apiHelper.getResponseValue(key);
        }
        logger.debug("URL : " + apiHelper.getApiUrl());
        logger.debug("Response : " + apiHelper.getStringResponse());

        logger.debug("expectedValue type : " + expectedValue.getClass().getSimpleName() + " expectedValue : " + expectedValue);
        logger.debug("currentValue type : " + currentValue.getClass().getSimpleName() + " currentValue : " + currentValue);
        Assert.assertNotEquals(expectedValue, currentValue.toString());
    }

    /**
     * Gelen yanıtın belirtilen alanını kontrol eder ve beklenen değerle karşılaştırır.
     *
     * @param key           Kontrol edilecek alanın anahtarı.
     * @param expectedValue Beklenen değer
     * @throws Exception Alan değeri kontrol edilemezse veya senaryo geçerli değilse bir hata oluşursa.
     */
    @And("verify response field {} contains {}")
    public void responseFieldIsContains(String key, Object expectedValue) throws Exception {
        try {
            Object currentValue;
            expectedValue = projectHelper.valuePrepare(expectedValue);
            if (key.startsWith("datastore")) {
                currentValue = iniHelper.getIniFileValue(key.split(" ")[1]);
            } else {
                currentValue = apiHelper.getResponseValue(key);
            }

            logger.debug("expectedValue type : " + expectedValue.getClass().getSimpleName() + " expectedValue : " + expectedValue);
            logger.debug("currentValue type : " + currentValue.getClass().getSimpleName() + " currentValue : " + currentValue);
            Assert.assertTrue(currentValue.toString().contains(expectedValue.toString()));

            logger.debug("Step : response field " + key + " contains expected " + expectedValue + " - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : ApiBaseStepDefs - responseFieldIs");
            throw new Exception(e.getMessage());
        }
    }




    @Given("prepare new request")
    public void prepareNewRequest() throws Exception {
        try {
            apiHelper.prepareForNewRequest();
            logger.debug("Step : prepare new request");
        } catch (Exception e) {
            logger.error("The method causing the error : ApiBaseStepDefs - prepareNewRequest");
            throw new Exception(e.getMessage());
        }
    }

    @And("update url (" + GherkinHelper.apiConfigDataCaseList + "){} as {} with (datastore|response|word){}")
    public void updateUrl(String urlName, String newUrlName, String givenValue) throws Exception {
        try {
            String urlValue = projectHelper.valuePrepare(givenValue).toString();
            apiHelper.setApiUrl(null);

            // Get url conditions
            if (!urlName.endsWith("Url")) {
                apiHelper.setApiUrl(iniHelper.getIniFileValue(urlName));
            } else {
                apiHelper.setApiUrl(apiConfigHelper.getApiConfigData(urlName));
            }

            // set url
            apiHelper.setApiUrl(apiHelper.getApiUrl() + "/" + urlValue);
            iniHelper.writeIniFile(newUrlName, apiHelper.getApiUrl());

            System.out.println("New URLLLL : " + iniHelper.getIniFileValue(newUrlName));

            logger.debug("Step : update url " + urlName + " to url " + newUrlName + " with " + givenValue + " - is success");
            logger.info("Url : " + apiHelper.getApiUrl() + " saved to datastore as name :" + newUrlName);
        } catch (Exception e) {
            logger.error("The method causing the error : ApiBaseStepDefs - updateUrl");
            throw new Exception(e.getMessage());
        }
    }

}