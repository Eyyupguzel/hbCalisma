package stepdefs.ui;

import helpers.ui.DriverHelper;
import helpers.ui.LocatorHelper;
import helpers.ui.UiConfigHelper;
import helpers.ui.UiHelper;
import helpers.base.IniHelper;
import helpers.base.GherkinHelper;
import helpers.base.Logger;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;

import java.util.Objects;


public class UiBaseStepDefs {
    public org.apache.log4j.Logger logger = Logger.getLogger(Logger.class);
    private final UiHelper uiHelper;
    private final DriverHelper driverHelper;
    private final LocatorHelper locatorHelper;
    private final UiConfigHelper uiConfigHelper;
    private final IniHelper iniHelper;
    public boolean stepResult;


    public UiBaseStepDefs(LocatorHelper locatorHelper, UiHelper uiHelper, UiConfigHelper uiConfigHelper, IniHelper iniHelper, DriverHelper driverHelper) {
        this.locatorHelper = locatorHelper;
        this.driverHelper = driverHelper;
        this.uiHelper = uiHelper;
        this.uiConfigHelper = uiConfigHelper;
        this.iniHelper = iniHelper;
    }

    /**
     * Belirtilen sayfaya belirtilen tarayıcı parametreleriyle gider.
     *
     * @param pageName          Sayfa adı
     * @param browserParameters Tarayıcı parametreleri
     * @return İşlem başarılı ise true, aksi halde false
     * @throws Exception Hata oluştuğunda fırlatılır
     */
    @Given("go to (" + GherkinHelper.pageNames + "){} page with (Chrome|ChromeWithMetamask|Firefox|Safari|Edge|default|newTab){}")
    public boolean goToPage(String pageName, String browserParameters) throws Exception {
        // Example Sentences
        // Given go to land page with Chrome and Metamask
        // * go to land page with Chrome

        try {
            // Browser Conditions
            driverHelper.getCurrentDriver(browserParameters);

            // URL Conditions
            if (pageName.startsWith("http") || pageName.startsWith("chrome-extension")) {
                uiHelper.setUrl(pageName);
                int length = pageName.split("/").length;
                pageName = pageName.split("/")[length - 1];
                uiHelper.setPageName(pageName);
                System.out.println("setttedd" + pageName);
            } else {
                uiHelper.setUrl(uiConfigHelper.getUiConfigData(pageName + "WebUrl"));
                uiHelper.setPageName(pageName);
            }

            // Actions
            if (Objects.equals(browserParameters, "newTab")) {
                uiHelper.openNewTab();
                uiHelper.driverGoToUrl(uiHelper.getUrl());
            } else {
                System.out.println("URLLLLLLL : " + uiHelper.getUrl());
                stepResult = uiHelper.driverGoToUrl(uiHelper.getUrl());
            }

            logger.debug("Step : go to " + pageName + " page - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : UIBaseStepDefs - goToPage");
            throw new Exception(e.getMessage());
        }
        return stepResult;
    }


    @When("verify with datastore key {}, data {}")
    public boolean verifyWithDatastore(String key, String data) throws Exception {
        try {
            String datastoreValue = iniHelper.getIniFileValue(key);
            if (Objects.equals(datastoreValue, data)) {
                stepResult = true;
            }
        } catch (Exception e) {
            logger.error("The method causing the error : UIBaseStepDefs - elementAction");
            throw new Exception(e.getMessage());
        }
        return stepResult;
    }

    @When("i (" + GherkinHelper.elementActionsCaseList + "){} element {}")
    public boolean elementAction(String action, String elementName) throws Exception {
        // Example Sentences
        // When click land page element exampleElementName
        // When send keys land page element exampleElementName
        // (Burada exampleElementName src/test/java/helpers/elementHelper/elements/...json dosyasından okunur)
        // json dosyasında name keyine göre elementi bulur. Bulduğu elementin id veya xpath veya yakalama methodu neyse onu method olarak belirler

        try {
            WebElement webElement;
            String pageName = null;
            String locator;
            String method;
            String value;
            String[] parts;

            //pageName Conditions
            pageName = uiHelper.getPageName();
            System.out.println(pageName + "pagename");
            locator = locatorHelper.getElementLocatorWithJson(pageName, elementName);
            method = locatorHelper.getElementMethodWithJson(pageName, elementName);

            webElement = uiHelper.getWebElement(locator, method);
            stepResult = uiHelper.elementActions(webElement, action);

            logger.debug("Step : " + pageName + " page " + action + " element " + elementName + " - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : UIBaseStepDefs - elementAction");
            throw new Exception(e.getMessage());
        }
        return stepResult;
    }


    @When("send keys {} to element {}")
    public boolean sendKeysAction(String keys, String elementName) throws Exception {
        // Example Sentences
        // When click land page element exampleElementName
        // When i send keys land page element exampleElementName
        // (Burada exampleElementName src/test/java/helpers/elementHelper/elements/...json dosyasından okunur)
        // json dosyasında name keyine göre elementi bulur. Bulduğu elementin id veya xpath veya yakalama methodu neyse onu method olarak belirler

        try {
            WebElement webElement;
            String pageName;
            String locator;
            String method;
            //pageName Conditions
            pageName = uiHelper.getPageName();
            System.out.println(pageName + " page");

            locator = locatorHelper.getElementLocatorWithJson(pageName, elementName);
            method = locatorHelper.getElementMethodWithJson(pageName, elementName);

            webElement = uiHelper.getWebElement(locator, method);
            stepResult = uiHelper.sendKeys(webElement, keys);

            logger.debug("Step : " + "i send keys " + keys + " element " + elementName + " - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : UIBaseStepDefs - sendKeysAction");
            throw new Exception(e.getMessage());
        }
        return stepResult;
    }

    @When("force send keys {} to element {}")
    public boolean forceSendKeysAction(String keys, String elementName) throws Exception {
        // Example Sentences
        // When click land page element exampleElementName
        // When i send keys land page element exampleElementName
        // (Burada exampleElementName src/test/java/helpers/elementHelper/elements/...json dosyasından okunur)
        // json dosyasında name keyine göre elementi bulur. Bulduğu elementin id veya xpath veya yakalama methodu neyse onu method olarak belirler

        try {
            WebElement webElement;
            String pageName;
            String locator;
            String method;
            //pageName Conditions
            pageName = uiHelper.getPageName();
            System.out.println(pageName + " page");

            locator = locatorHelper.getElementLocatorWithJson(pageName, elementName);
            method = locatorHelper.getElementMethodWithJson(pageName, elementName);

            webElement = uiHelper.getWebElement(locator, method);
            stepResult = uiHelper.forceSendKeys(webElement, keys);

            logger.debug("Step : " + "i send keys " + keys + " element " + elementName + " - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : UIBaseStepDefs - sendKeysAction");
            throw new Exception(e.getMessage());
        }
        return stepResult;
    }    /**
     * Sürücü üzerinde belirtilen eylemi gerçekleştirir.
     *
     * @param action Eylem
     * @return İşlem başarılı ise true, aksi halde false
     * @throws Exception Hata oluştuğunda fırlatılır
     */
    @Then("driver (" + GherkinHelper.driverActionsCaseList + "){}")
    public boolean driverAction(String action) throws Exception {
        // Example Sentences
        // * driver close
        // * driver refresh
        // * driver forward
        // * driver scroll down
        // Bu method driver'a base işlemler yaptırmak için kullanılır
        try {
            stepResult = uiHelper.driverActions(action);
            logger.debug("Step : driver " + action + " - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : UIBaseStepDefs - driverAction");
            throw new Exception(e.getMessage());
        }
        return stepResult;
    }

    @Given("wait {} seconds")
    public boolean driverWait(int waitTime) throws Exception {
        try {
            uiHelper.independentWait(waitTime);
        } catch (Exception e) {
            logger.error("\t The method causing the error : UIBaseStepDefs - driverManuelWait");
            throw new Exception(e.getMessage());
        }
        return true;
    }


    /**
     * Beklenen URL'yi doğrular.
     *
     * @param expectedUrl Beklenen URL
     * @return İşlem başarılı ise true, aksi halde false
     * @throws Exception Hata oluştuğunda fırlatılır
     */
    @And("verify url {}")
    public boolean verifyUrl(String expectedUrl) throws Exception {
        // Bu method url i kontrol eder
        try {
            Thread.sleep(2000);
            uiHelper.verifyURL(expectedUrl);
            logger.debug("Step : verifyUrl" + expectedUrl + " - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : UIBaseStepDefs - verifyUrl");
            throw new Exception(e.getMessage());
        }
        return stepResult;
    }

    /**
     * Son pencereye geçiş yapar.
     *
     * @return İşlem başarılı ise true, aksi halde false
     * @throws Exception Hata oluştuğunda fırlatılır
     */
    @And("switch last window")
    public boolean switchLastWindow() throws Exception {
        // Bu method son sayfayı aktifleştirir
        try {
            Thread.sleep(2000);
            uiHelper.switchToLastWindow();
            logger.debug("Step : switch last window - is success");
        } catch (Exception e) {
            logger.error("The method causing the error : UIBaseStepDefs - switchLastWindow");
            throw new Exception(e.getMessage());
        }
        return stepResult;
    }



}
