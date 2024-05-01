package helpers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class LocatorHelper {
    private final String baseFilePath = "src/test/java/utils/webUtils/elements/";

    public LocatorHelper() {

    }

    public String getElementLocatorWithJson(String pageName, String elementText) throws IOException {
        String filePath = baseFilePath + pageName + ".json";
        String json = new String(Files.readAllBytes(Paths.get(filePath)));
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);

        String xpath = null;
        boolean found = false;
        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();

            if (obj.get("name").getAsString().equals(elementText)) {
                xpath = obj.get("locator").getAsString();
                found = true; //burada name'in eşletiğini boolean bir değişken ile tutuyoruz
                break;
            }
        }
        if (!found) { //burada name eşleşmiyorsa değer gherkin tarafında verilen değer yanlış girilmiştir.Böylelikle hata fırlatmamız gerekir.
            throw new RuntimeException("Element not found: " + elementText);
        }

        return xpath;
    }

    public String getElementMethodWithJson(String pageName, String elementText) throws IOException {
        String filePath = baseFilePath + pageName + ".json";
        String json = new String(Files.readAllBytes(Paths.get(filePath)));

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);

        String method = null;
        boolean found = false;
        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();
            if (obj.get("name").getAsString().equals(elementText)) {
                method = obj.get("method").getAsString();
                found = true;//burada name'in eşletiğini boolean bir değişken ile tutuyoruz
                break;
            }
        }
        if (!found) {//burada name eşleşmiyorsa değer gherkin tarafında verilen değer yanlış girilmiştir.Böylelikle hata fırlatmamız gerekir.
            throw new RuntimeException("Element not found: " + elementText);
        }
        return method;
    }

    public boolean doesJsonFileExist(String pageName) {
        File file = new File(baseFilePath, pageName + ".json");
        return file.exists() && file.isFile();
    }

    public static void main(String[] args) {
        LocatorHelper locatorHelper = new LocatorHelper();
        String pageName = "example";

        boolean fileExists = locatorHelper.doesJsonFileExist(pageName);
        if (fileExists) {
            System.out.println("JSON dosyası mevcut.");
        } else {
            System.out.println("JSON dosyası bulunamadı.");
        }
    }
}
