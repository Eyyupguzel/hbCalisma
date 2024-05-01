package helpers.ui;

import helpers.base.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;

public class BrowserOptionHelper {
    public final org.apache.log4j.Logger logger = Logger.getLogger(Logger.class);
    private ChromeOptions chromeOptions;
    private FirefoxOptions firefoxOptions;
    private EdgeOptions edgeOptions;
    private SafariOptions safariOptions;

    /**
     * Genel Chrome seçeneklerini oluşturur.
     *
     * @return Oluşturulan Chrome seçenekleri.
     */
    public ChromeOptions generalChromeOptions() {
        try {
            chromeOptions = new ChromeOptions();
            chromeOptions = chromeOptions.addArguments(
                    "--start-maximized",
                    "--remote-allow-origins=*",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-notifications",
                    "--disable-blink-features=AutomationControlled",
                    "--incognito"
//                   "--headless"
            );
            chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            logger.info("\t Driver - Chrome options done");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return chromeOptions;
    }

    /**
     * Genel Firefox seçeneklerini oluşturur.
     *
     * @return Oluşturulan Firefox seçenekleri.
     */
    public FirefoxOptions generalFirefoxOptions() {
        try {
            firefoxOptions = new FirefoxOptions();
            firefoxOptions = firefoxOptions.addArguments(
                    "--start-maximized",
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
                    //, "--headless"
            );
            logger.info("\t Driver - Firefox options done");
            return firefoxOptions;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return firefoxOptions;
    }

    /**
     * Genel Edge seçeneklerini oluşturur.
     *
     * @return Oluşturulan Edge seçenekleri.
     */
    public EdgeOptions generalEdgeOptions() {
        try {
            edgeOptions = new EdgeOptions();
            edgeOptions = edgeOptions.addArguments(
                    "--start-maximized",
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
                    //, "--headless"
            );
            logger.info("\t Driver - Edge options done");
            return edgeOptions;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return edgeOptions;
    }

    /**
     * Genel Safari seçeneklerini oluşturur.
     *
     * @return Oluşturulan Safari seçenekleri.
     */
    public SafariOptions generalSafariOptions() {
        try {
            safariOptions = new SafariOptions();
            safariOptions.setCapability("start-maximized", true);
            safariOptions.setCapability("no-sandbox", true);
            safariOptions.setCapability("disable-dev-shm-usage", true);
            // safariOptions.setCapability("headless", true);
            logger.info("\t Driver - Safari options done");
            return safariOptions;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return safariOptions;
    }
}
