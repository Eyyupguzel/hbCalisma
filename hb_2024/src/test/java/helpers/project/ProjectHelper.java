package helpers.project;

import helpers.api.ApiConfigHelper;
import helpers.api.ApiHelper;
import helpers.base.IniHelper;
import helpers.base.Logger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProjectHelper {
    public org.apache.log4j.Logger logger = Logger.getLogger(Logger.class);
    private ApiHelper apiHelper;
    private ApiConfigHelper apiConfigHelper;
    private final IniHelper iniHelper;

    public ProjectHelper(ApiConfigHelper apiConfigHelper, IniHelper iniHelper, ApiHelper apiHelper) {
        this.iniHelper = iniHelper;
        this.apiHelper = apiHelper;
        this.apiConfigHelper = apiConfigHelper;
    }

    /**
     * Veriyi veri deposuna (INI dosyasına) yerleştiren yöntem.
     *
     * @param key   Veri deposunda kullanılacak anahtar
     * @param value Veri deposuna yerleştirilecek değer
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public void putDataStore(String key, Object value) throws Exception {
        try {
            String stringValue = value.toString();
            iniHelper.writeIniFile(key, stringValue);
        } catch (Exception e) {
            logger.error("The method causing the error : ProjectHelper - putDataStore");
            throw new Exception(e.getMessage());
        }
    }

    public Object valuePrepare(Object value) throws Exception {
        try {
            if (value.toString().startsWith("datastore")) {
                value = iniHelper.getIniFileValue(value.toString().split(" ")[1]);
            } else if (value.toString().startsWith("word")) {
                value = value.toString().split(" ")[1];
            } else if (value.toString().startsWith("response")) {
                value = value.toString().split(" ")[1];
                value = apiHelper.getResponseValue(value);
            } else if (value.toString().endsWith("Url")) {
                value = apiConfigHelper.getApiConfigData(value.toString());
            }else if (value.toString().startsWith("null")) {
                value = "null";
            }
            logger.debug("Prepared Value : " + value);
        } catch (Exception e) {
            logger.error("The method causing the error : ProjectHelper - valuePrepare");
            logger.debug("Not prepared Value : " + value);
            throw new Exception(e.getMessage());
        }
        return value;
    }

}
