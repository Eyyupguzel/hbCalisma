package helpers.ui;

import helpers.base.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;

public class DriverHelper {
    public final org.apache.log4j.Logger logger = Logger.getLogger(Logger.class);
    private WebDriver driver;
    private String browserName;
    private final UiConfigHelper uiConfigHelper;
    private final BrowserOptionHelper browserOptionHelper;
    private final WaitHelper waitHelper;
    private final WebDriverManager autoChromeDriver = WebDriverManager.chromedriver().clearDriverCache();
    private final WebDriverManager autoFirefoxDriver = WebDriverManager.firefoxdriver();
    private final WebDriverManager autoSafariDriver = WebDriverManager.safaridriver();
    private final WebDriverManager autoEdgeDriver = WebDriverManager.edgedriver();


    public DriverHelper(UiConfigHelper uiConfigHelper, WaitHelper waitHelper, BrowserOptionHelper browserOptionHelper) {
        this.uiConfigHelper = uiConfigHelper;
        this.waitHelper = waitHelper;
        this.browserOptionHelper = browserOptionHelper;
        logger.info("\t Driver - Driver constructor working and TestConfig called");
    }

    public WebDriver getCurrentDriver(String specifiedBrowser) {
        try {
            if (driver == null) {
                try {
                    driver = setupDrivers(specifiedBrowser);
                } catch (Exception e) {
                    logger.error("\t Driver - Driver not initialized");
                    logger.error(e.getMessage());
                }
                logger.info("\t Driver - getCurrentDriver - Driver was null and initialized");
            } else {
                logger.info("\t Driver - getCurrentDriver - Driver was not null current driver using");
                return driver;
            }
        } catch (Exception e) {
            logger.error("\t Driver - Current river not initialized");
            throw new RuntimeException("Exception : " + browserName + e.getMessage());
        }
        return driver;
    }


    public WebDriver setupDrivers(String specifiedBrowser) {
        try {
            if (specifiedBrowser != null) {
                if (specifiedBrowser.equals("default")) {
                    browserName = uiConfigHelper.getBrowserName();
                } else {
                    browserName = specifiedBrowser;
                }
                logger.info("\t Driver - Browser name specified and changed : " + browserName);
            } else {
                logger.info("\t Driver - Browser name does not correct : " + browserName);
            }
            switch (browserName) {
                case "Chrome":
                    autoChromeDriver.setup();
                    driver = new ChromeDriver(browserOptionHelper.generalChromeOptions());
                    break;
                case "Firefox":
                    autoFirefoxDriver.setup();
                    driver = new FirefoxDriver(browserOptionHelper.generalFirefoxOptions());
                    break;
                case "Safari":
                    autoSafariDriver.setup();
                    driver = new SafariDriver(browserOptionHelper.generalSafariOptions());
                    break;
                case "Edge":
                    autoEdgeDriver.setup();
                    driver = new EdgeDriver(browserOptionHelper.generalEdgeOptions());
                    break;
            }
            logger.info("\t Driver - " + browserName + " driver setup done");
            waitHelper.generalImplicitWait(driver);
        } catch (Exception e) {
            logger.error("\t Driver - " + browserName + " driver setup failed");
            throw new RuntimeException("Exception : " + browserName + e.getMessage());
        }
        return driver;
    }
    public WebDriver getDriver() {
        return driver;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public BrowserOptionHelper getBrowserOptionHelper() {
        return browserOptionHelper;
    }

    public WaitHelper getWaitHelper() {
        return waitHelper;
    }

}
