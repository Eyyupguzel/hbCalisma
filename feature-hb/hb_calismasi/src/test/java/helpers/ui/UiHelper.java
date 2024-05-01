package helpers.ui;

import helpers.base.IniHelper;
import helpers.base.Logger;
import helpers.project.ProjectHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import stepdefs.ui.UiBaseStepDefs;

import java.time.Duration;
import java.util.Set;

public class UiHelper {
    public final org.apache.log4j.Logger logger = Logger.getLogger(Logger.class);
    private final ProjectHelper projectHelper;
    private final UiConfigHelper uiConfigHelper;
    private final IniHelper iniHelper;
    private final WaitHelper waitHelper;
    private String webUrl;
    private String pageName;
    private DriverHelper driverHelper;


    public UiHelper(WaitHelper waitHelper,IniHelper iniHelper,ProjectHelper projectHelper, DriverHelper driverHelper, UiConfigHelper uiConfigHelper) throws Exception {
        this.uiConfigHelper = uiConfigHelper;
        this.waitHelper = waitHelper;
        this.projectHelper = projectHelper;
        this.iniHelper = iniHelper;
        this.driverHelper = driverHelper;

        try {
            assert driverHelper.getDriver() != null;
            logger.info("\t UiHelper - Driver is not null");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - constructor");
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Belirtilen web elementinin tıklanabilir olup olmadığını kontrol etmek için kullanılan method.
     * @param webElement Kontrol edilecek web elementi
     * @return Element tıklanabilir ise true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean isClickable(WebElement webElement) throws Exception {
        // Bu method ile elementin tıklanabilir olup olmadığı kontrol edilir
        try {
            waitHelper.generalExplicitWait(driverHelper.getDriver()).until(ExpectedConditions.elementToBeClickable(webElement));
            logger.info("\t UiHelper - is clickable." + webElement);

        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - isClickable." + webElement);
            logger.error("Exception : " + webElement);
            throw new Exception();
        }
        return true;
    }

    /**
     * Belirtilen web elementinin tıklanabilir olmamasını kontrol etmek için kullanılan method.
     * @param webElement Kontrol edilecek web elementi
     * @return Element tıklanabilir değilse true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean isNotClickable(WebElement webElement) throws Exception {
        // Bu method ile elementin tıklanabilir olmaması kontrol edilir
        try {
            if (!isClickable(webElement)) {
                logger.info(webElement + " UiHelper - is not clickable");
            }
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - isNotClickable." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen web elementinin sayfada görünür olmasını beklemek için kullanılan method.
     * @param webElement Görünür olması beklenen web elementi
     * @return Element görünür hale geldiyse true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean isVisible(WebElement webElement) throws Exception {
        // Bu method ile sayfada elementin görünür olması beklenir.
        try {
            waitHelper.generalExplicitWait(driverHelper.getDriver()).until(ExpectedConditions.visibilityOf(webElement));
            logger.info("UiHelper - Element " + webElement + " is visible");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - isVisible." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen web elementinin sayfada görünmez olmasını beklemek için kullanılan method.
     * @param webElement Görünmez olması beklenen web elementi
     * @return Element görünmez hale geldiyse true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean isInvisibleElement(WebElement webElement) throws Exception {
        // Bu method ile sayfada elementin görünür olmamasını bekler.
        try {
            waitHelper.generalExplicitWait(driverHelper.getDriver()).until(ExpectedConditions.invisibilityOf(webElement));
            logger.info("UiHelper - Element " + webElement + " is invisible");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - isInvisibleElement." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen web elementinin görünür olup olmadığını kontrol etmek için kullanılan method.
     * @param webElement Kontrol edilecek web elementi
     * @return Element görünür ise true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean isDisplayed(WebElement webElement) throws Exception {
        // Bu method ile elementin görünür olması kontrol edilir
        try {
            webElement.isDisplayed();
            logger.info("\t UiHelper - is displayed." + webElement);
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - isDisplayed." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen web elementinin seçili olup olmadığını kontrol etmek için kullanılan method.
     * @param webElement Kontrol edilecek web elementi
     * @return Element seçili ise true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean isSelected(WebElement webElement) throws Exception {
        try {
            webElement.isSelected();
            logger.info("\t UiHelper - is selected." + webElement);
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - isSelected." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen web elementinin etkin olup olmadığını kontrol etmek için kullanılan method.
     * @param webElement Kontrol edilecek web elementi
     * @return Element etkin ise true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean isEnabled(WebElement webElement) throws Exception {
        // Bu method ile elementin görünür olması kontrol edilir
        try {
            webElement.isEnabled();
            logger.info("\t UiHelper - is enabled." + webElement);
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - isEnabled." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen web elementinin üzerine gelmek için kullanılan method.
     * @param webElement Üzerine gelinilecek web elementi
     * @return İşlem başarılı ise true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean hoverElement(WebElement webElement) throws Exception {
        // Bu method ile element üzerinde bekleme yapılır
        try {
            Actions actions = new Actions(driverHelper.getDriver());
            actions.moveToElement(webElement).build().perform();
            logger.info("UiHelper - Element " + webElement + " is hovered");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - hoverElement." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen web elementine tıklama yapmak için kullanılan method.
     * @param webElement Tıklama yapılacak web elementi
     * @return İşlem başarılı ise true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean clickElement(WebElement webElement) throws Exception {
        // Bu method ile elemente tıklanır
        try {

            if (isVisible(webElement) && isClickable(webElement)) {
                webElement.click();
                logger.info("UiHelper - Clicked " + webElement);
            }
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - clickElement." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    public boolean clickElementIf(WebElement webElement) throws Exception {
        // Bu method ile elemente tıklanır
        try {

            if (isVisible(webElement) && isClickable(webElement)) {
                webElement.click();
                logger.info("UiHelper - Clicked " + webElement);
            }
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - clickElement." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }


    /**
     * Belirtilen web elementine zorla tıklama yapmak için kullanılan method.
     *
     * @param webElement Tıklama yapılacak web elementi
     * @return İşlem başarılı ise true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean forceClick(WebElement webElement) throws Exception {
        // Bu method zorla tıklama yapmak için kullanılır
        try {
            if (isVisible(webElement) && isClickable(webElement)) {
                JavascriptExecutor executor = (JavascriptExecutor) driverHelper.getDriver();
                executor.executeScript("arguments[0].click();", webElement);
            }
            logger.info("\t UiHelper - Force clicked" + webElement);
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - force forceClick" + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen web elementine metin göndermek için kullanılan method.
     * @param webElement Metin gönderilecek web elementi
     * @param value Gönderilecek metin
     * @return İşlem başarılı ise true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean sendKeys(WebElement webElement, String value) throws Exception {
        // Bu method ile elemente bir metin gönderilir
        try {
            if (isDisplayed(webElement)) {

                webElement.clear();
                webElement.sendKeys(value);
                logger.info("UiHelper - Entered text: " + value + " into " + webElement);
            }
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - sendKeys." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }
    public boolean forceSendKeys(WebElement webElement, String value) throws Exception {
        // Bu method ile elemente bir metin gönderilir
        try {
            if (isDisplayed(webElement)&& isVisible(webElement)) {
                Actions actions = new Actions(driverHelper.getDriver());
                actions.sendKeys(webElement).sendKeys(value).perform();
                // webElement.sendKeys(value);
                logger.info("UiHelper - Entered text: " + value + " into " + webElement);
            }
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - sendKeys." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen web elementin içeriğini temizlemek için kullanılan method.
     * @param webElement İçeriği temizlenecek web elementi
     * @return İşlem başarılı ise true, aksi halde false döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean elementClear(WebElement webElement) throws Exception {
        // Bu method ile elemente bir metin gönderilir
        try {
            if (isVisible(webElement)) {
                webElement.clear();
                logger.info("UiHelper - Element clear" + webElement);
            }
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - elementClear." + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen web elementin içindeki metni almak için kullanılan method.
     * @param webElement Metni alınacak web elementi
     * @return Web elementin içindeki metin
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public String getElementText(WebElement webElement) throws Exception {
        // Bu method ile element içindeki bir değerin kontrolü gerçekleştirilir
        String text = null;
        try {
            if (isDisplayed(webElement)) {
                text = webElement.getText();
                logger.info("UiHelper - Value\t" + text + "\t verified.");
            }
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - getElementText." + webElement);
            throw new Exception(e.getMessage());
        }
        return text;
    }

    /**
     * Yeni bir sekme açmak için kullanılan method.
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean openNewTab() throws Exception {
        // Bu method ile yeni bir sekme açılır
        try {
            driverHelper.getDriver().switchTo().newWindow(WindowType.TAB);
            logger.info("UiHelper - New tab opened");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - openNewTab");
            throw new Exception(e.getMessage());
        }
        return true;
    }

    public boolean forceScroll(WebElement webElement) throws Exception {
        // Bu method zorla tıklama yapmak için kullanılır
        try {
            JavascriptExecutor jsexecutor = ((JavascriptExecutor) driverHelper.getDriver());
            jsexecutor.executeScript("arguments[0].scrollIntoView(true);", webElement);
            logger.info("\t UiHelper - Force clicked" + webElement);
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - force forceClick" + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen WebElement'e kadar sayfayı kaydırmak için kullanılan method.
     * @param webElement Kaydırma yapılacak WebElement
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean scrollToElement(WebElement webElement) throws Exception {
        try {
            Thread.sleep(1000);
            if (isDisplayed(webElement)) {
                waitHelper.generalExplicitWait(driverHelper.getDriver()).until(ExpectedConditions.visibilityOf(webElement));
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driverHelper.getDriver();
                jsExecutor.executeScript("arguments[0].scrollIntoView();", webElement);
                logger.info("UiHelper - Scroll to element");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - scrollToElemet");
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Aktif pencerenin URL'sini doğrulamak için kullanılan method.
     * @param expectedResult Beklenen URL'nin parçası olan bir dize
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean verifyURL(String expectedResult) throws Exception {
        // Bu method aktif pencerenin URL bilgisini doğrulamak için kullanılır
        String URL = null;
        try {
            URL = driverHelper.getDriver().getCurrentUrl();
            Assert.assertTrue(URL.contains(expectedResult), "URL\t" + URL + "\tnot virified. ");
            logger.info("UiHelper - URL \t" + URL + "\tverified");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - verifyUrl");
            throw new Exception(e.getMessage());
        }
        return true;
    }


    /**
     * Son pencereye geçiş yapmak için kullanılan method.
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean switchToLastWindow() throws Exception {
        try {
            Set<String> windowHandles = driverHelper.getDriver().getWindowHandles();
            String lastWindowHandle = windowHandles.iterator().next();
            for (String windowHandle : windowHandles) {
                lastWindowHandle = windowHandle;
            }
            driverHelper.getDriver().switchTo().window(lastWindowHandle);
            logger.info("\t UiHelper - Switched last window");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - switchToLastWindow");
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Başlıkla belirtilen pencereye geçiş yapmak için kullanılan method.
     * @param title Geçiş yapılacak pencerenin başlığı
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean switchToWindowByTitle(String title) throws Exception {
        try {
            String currentTab = driverHelper.getDriver().getWindowHandle();
            for (String tab : driverHelper.getDriver().getWindowHandles()) {
                driverHelper.getDriver().switchTo().window(tab);
                if (driverHelper.getDriver().getTitle().equals(title)) {
                    break;
                }
            }
            driverHelper.getDriver().switchTo().window(currentTab);
            logger.info("\t UiHelper - Switched window by title");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - switchToWindowByTitle");
            throw new Exception(e.getMessage());
        }
        return true;
    }



    /**
     * Sürücüyü tamamen kapatmak için kullanılan method.
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean driverQuit() throws Exception {
        try {
            driverHelper.getDriver().quit();
            logger.info("\t UiHelper - Driver quit");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - driverQuit");
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Aktif pencereyi kapatmak için kullanılan method.
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean driverClose() throws Exception {
        try {
            driverHelper.getDriver().close();
            logger.info("\t UiHelper - Driver close");

        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - driverClose");
            throw new Exception(e.getMessage());
        }
        return true;
    }



    /**
     * Bağımsız bekleme süresi için kullanılan method.
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean independentWait(int wait) throws Exception {
        try {
            Thread.sleep(wait* 1000L);
            logger.info("\t UiHelper - Independent wait");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - independentWait");
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Sayfa yüklenmesini beklemek için kullanılan method.
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean pageLoadWait() throws Exception {
        try {
            driverHelper.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(uiConfigHelper.getPageLoadWait()));
            logger.info("\t UiHelper - Page Load wait");
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - pageLoadWait");
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen URL'ye gitmek için kullanılan method.
     * @param webUrl Gitmek istenilen URL
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean driverGoToUrl(String webUrl) throws Exception {
        try {
            System.out.println(driverHelper.getDriver());
            driverHelper.getDriver().get(webUrl);
            pageLoadWait();

            logger.info("\t UiHelper - Go to webUrl");
            return true;
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - driverGoToUrl");
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Belirtilen lokatör ve yönteme göre web elementini bulmak için kullanılan method.
     * @param locator Web elementini bulmak için kullanılacak lokatör
     * @param method Web elementini bulmak için kullanılacak method
     * @return Bulunan web elementi
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public WebElement getWebElement(String locator, String method) throws Exception {
        // Bu method ile elementin görünür olması kontrol edilir ve webelement döndürülür
        WebElement webElement = null;
        try {
            switch (method) {
                case "xpath":
                    webElement = waitHelper.generalExplicitWait(driverHelper.getDriver()).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
                    break;
                case "id":
                    webElement = waitHelper.generalExplicitWait(driverHelper.getDriver()).until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));
                    break;
                case "css":
                    webElement = waitHelper.generalExplicitWait(driverHelper.getDriver()).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locator)));
                    break;
                case "name":
                    webElement = waitHelper.generalExplicitWait(driverHelper.getDriver()).until(ExpectedConditions.presenceOfElementLocated(By.name(locator)));
                    break;
                case "linkText":
                    webElement = waitHelper.generalExplicitWait(driverHelper.getDriver()).until(ExpectedConditions.presenceOfElementLocated(By.linkText(locator)));
                    break;
                case "partialLinkText":
                    webElement = waitHelper.generalExplicitWait(driverHelper.getDriver()).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locator)));
                    break;
                default:
                    logger.error(locator + " : UiHelper - getWebElement - Element is not found, please control element json path in TestConfig ");
                    throw new UnsupportedOperationException("Invalid action: " + method);
            }
            logger.info("\t UiHelper - getWebElement - " + locator);

        } catch (RuntimeException e) {
            logger.error("\t The method causing the error : UiHelper - getWebElement" + locator);
            logger.error("Exception : " + locator + e.getMessage());
            throw new Exception(e.getMessage());
        }
        return webElement;
    }

    /**
     * Belirtilen web elementi üzerinde gerçekleştirilen eylemleri yönetmek için kullanılan method.
     * @param webElement İşlem yapılacak web elementi
     * @param action Gerçekleştirilecek eylem
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean elementActions(WebElement webElement, String action) throws Exception {
        try {
            switch (action) {
                case "click":
                    clickElement(webElement);
                    break;
                case "forceClick":
                    forceClick(webElement);
                    break;
                case "forceScroll":
                    forceScroll(webElement);
                    break;
                case "sendKeys":
                    //clickElement(webElement);
                    sendKeys(webElement, iniHelper.getIniFileValue("value"));
                    break;
                case "forceSendKeys":
                    //clickElement(webElement);
                    forceSendKeys(webElement, iniHelper.getIniFileValue("value"));
                    break;
                case "getText":
                    String text = getElementText(webElement);
                    projectHelper.putDataStore("text", text);
                    break;
                case "clearInput":
                    elementClear(webElement);
                    break;
                case "isDisplay":
                    isDisplayed(webElement);
                    break;
                case "isSelected":
                    isSelected(webElement);
                    break;
                case "isEnabled":
                    isEnabled(webElement);
                    break;
                case "isVisible":
                    isVisible(webElement);
                    break;
                case "hover":
                    hoverElement(webElement);
                    break;
                case "isInvisible":
                    isInvisibleElement(webElement);
                    break;
                case "isClickable":
                    isClickable(webElement);
                    break;
                case "isNotClickable":
                    isNotClickable(webElement);
                    break;
                case "scrollToElement":
                    scrollToElement(webElement);
                    break;
                default:
                    logger.error("UiHelper - elementActions - Invalid action: " + action);
                    throw new UnsupportedOperationException("Invalid action: " + action);
            }
            logger.info("\t UiHelper - elementActions - " + action + webElement);
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - elementActions" + webElement);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * Belirtilen eylemi gerçekleştiren sürücü işlemlerini yönetmek için kullanılan method.
     * @param action Gerçekleştirilecek eylem
     * @return İşlemin başarıyla tamamlanması durumunda true değeri döner
     * @throws Exception İşlem sırasında bir hata oluştuğunda fırlatılır
     */
    public boolean driverActions(String action) throws Exception {
        try {
            // Actions
            switch (action) {
                case "quit":
                    driverQuit();
                    break;
                case "close":
                    driverClose();
                    break;
                case "switchToLastWindow":
                    switchToLastWindow();
                    break;
                default:
                    logger.error("UiHelper - driverActions - Invalid action: " + action);
                    throw new UnsupportedOperationException("Invalid action: " + action);
            }
            logger.info("\t UiHelper - driverActions - " + action);

        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - driverActions" + action);
            throw new Exception(e.getMessage());
        }
        return true;
    }



    public String setUrl (String webUrl){
        this.webUrl = webUrl;
        return webUrl;
    }
    public String getUrl (){
        return webUrl;
    }

    public String setPageName (String pageName){
        this.pageName = pageName;
        return pageName;
    }
    public String getPageName (){
        return pageName;
    }
}





