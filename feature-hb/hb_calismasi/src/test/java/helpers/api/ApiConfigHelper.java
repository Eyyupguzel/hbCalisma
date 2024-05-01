package helpers.api;

import helpers.base.Logger;

public class ApiConfigHelper {
    private final org.apache.log4j.Logger logger = Logger.getLogger(Logger.class);

    private final String baseApiUrl = "https://reqres.in/api";
    private final String registerUrl = "/register";
    private final String userUrl = "/users";

    /**
     * Belirtilen URL için ilgili yapılandırma verisini döndürür.
     *
     * @param specifiedUrl Belirli bir URL'nin yapılandırma verisi.
     * @return İlgili yapılandırma verisi.
     * @throws Exception Hata durumunda fırlatılır.
     */
    public String getApiConfigData(String specifiedUrl) throws Exception {
        try {
            switch (specifiedUrl) {
                case "baseApiUrl":
                    return baseApiUrl;
                case "registerUrl":
                    return baseApiUrl + registerUrl;
                case "userUrl":
                    return baseApiUrl + userUrl;
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.error("\t The method causing the error: TestConfig - getUiConfigData");
            throw new Exception(e.getMessage());
        }
    }


    public String getBaseApiUrl() {
        return baseApiUrl;
    }



}
