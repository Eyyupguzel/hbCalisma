package helpers.ui;

import helpers.base.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {
    public final org.apache.log4j.Logger logger = Logger.getLogger(Logger.class);

    private final UiConfigHelper uiConfigHelper;
    private WebDriverWait explicitWait;
    private WebDriver.Timeouts implictWait;

    public WaitHelper(UiConfigHelper uiConfigHelper) {
        this.uiConfigHelper = uiConfigHelper;
    }

    /**
     * Genel geçer beklemeyi (explicit wait) ayarlamak için kullanılan yöntem.
     * Explicit Wait: Belirli bir koşulu karşılayana kadar WebDriver'ın beklemesini sağlar.
     * Bu, belirli bir süre boyunca veya belirli bir koşul gerçekleşene kadar beklemeyi sağlar.
     * Örneğin, bir elementin görünür hale gelmesini veya tıklanabilir olmasını beklemek için explicit wait kullanılabilir.
     * Explicit wait, yalnızca belirli bir element için geçerlidir ve elementin durumuna bağlı olarak farklı sürelerde bekleyebilir.
     * @param driver Ayarlanacak WebDriver nesnesi
     * @return Ayarlanan WebDriverWait nesnesi
     */
    public WebDriverWait generalExplicitWait(WebDriver driver) {
        try {
            assert driver != null;
            explicitWait = new WebDriverWait(driver, Duration.ofSeconds(uiConfigHelper.getExplicitWait()));
            logger.info("\t Driver - Explicit wait done");

        } catch (WebDriverException e) {
            logger.error(e.getMessage());
        }
        return explicitWait;
    }

    /**
     * Genel geçer beklemeyi (implicit wait) ayarlamak için kullanılan yöntem.
     * Implicit Wait: WebDriver'in her element araması sırasında uyguladığı varsayılan bekleme süresidir.
     * Bu süre, element bulunana kadar veya belirli bir süre geçene kadar WebDriver'ın beklemesini sağlar.
     * Eğer element hemen bulunamazsa, WebDriver belirtilen süreyi bekler ve ardından NoSuchElementException hatası fırlatılır.
     * Implicit wait, tüm element aramaları için geçerlidir.
     * @param driver Ayarlanacak WebDriver nesnesi
     * @return Ayarlanan Timeout nesnesi
     */
    public WebDriver.Timeouts generalImplicitWait(WebDriver driver) {
        try {
            assert driver != null;
            implictWait = driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(uiConfigHelper.getImplicitWait()));
            logger.info("\t Driver - Implicit wait done");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return implictWait;
    }

    public void verifyPageLoad(WebDriver driver, long timeout) throws Exception {
            try {
                System.out.println("Waiting for page to load...");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

                ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
                wait.until(pageLoadCondition);
            } catch (Throwable error) {
                System.out.println(
                        "Timeout waiting for Page Load Request to complete after " + timeout + " seconds");
            }
    }
}
