package stepdefs.project;


import helpers.api.ApiHelper;
import helpers.base.IniHelper;
import helpers.base.Logger;
import helpers.project.ProjectHelper;
import helpers.ui.DriverHelper;
import helpers.ui.LocatorHelper;
import helpers.ui.UiHelper;
import helpers.ui.WaitHelper;
import io.cucumber.core.cli.Main;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepdefs.api.ApiBaseStepDefs;
import stepdefs.ui.UiBaseStepDefs;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectStepDefs {
    public final org.apache.log4j.Logger logger = Logger.getLogger(Logger.class);
    private final ApiHelper apiHelper;
    private final UiHelper uiHelper;
    private final DriverHelper driverHelper;
    private final ApiBaseStepDefs apiBaseStepDefs;
    private final WaitHelper waitHelper;
    private final UiBaseStepDefs uiBaseStepDefs;
    private final LocatorHelper locatorHelper;
    private final ProjectHelper projectHelper;
    private final IniHelper iniHelper;


    public ProjectStepDefs(DriverHelper driverHelper, ApiHelper apiHelper,  ApiBaseStepDefs apiBaseStepDefs, UiHelper uiHelper,WaitHelper waitHelper,UiBaseStepDefs uiBaseStepDefs,LocatorHelper locatorHelper,ProjectHelper projectHelper,IniHelper iniHelper) {
        this.apiHelper = apiHelper;
        this.driverHelper = driverHelper;
        this.apiBaseStepDefs = apiBaseStepDefs;
        this.projectHelper = projectHelper;
        this.uiHelper = uiHelper;
        this.waitHelper = waitHelper;
        this.uiBaseStepDefs = uiBaseStepDefs;
        this.locatorHelper = locatorHelper;
        this.iniHelper = iniHelper;
    }

    @Given("run scenario {}")
    public Thread runScenario(String scenarioName) throws Exception {
        try {
            String[] cucumberOptions = {
                    "--glue", "stepdefs",
                    //"--tags", "@all",
                    "--name", "^" + scenarioName + "$",
                    "src/test/resources/features"
            };
            Main.run(cucumberOptions, Thread.currentThread().getContextClassLoader());

            return Thread.currentThread();
        } catch (Exception e) {
            logger.error("The method causing the error : ProjectStepDefs - runScenario");
            throw new Exception(e.getMessage());
        }
    }

    public Thread runScenarioFor(String scenarioName, ClassLoader classLoader) throws Exception {
        try {
            String[] cucumberOptions = {
                    "--glue", "stepdefs",
                    //"--tags", "@all",
                    "--name", "^" + scenarioName + "$",
                    "src/test/resources/features"
            };
            Main.run(cucumberOptions, classLoader);

            return Thread.currentThread();
        } catch (Exception e) {
            logger.error("The method causing the error : ProjectStepDefs - runScenario");
            throw new Exception(e.getMessage());
        }
    }


    @Given("paralel run scenario with the following scenarios")
    public void paralelRunScenario(DataTable scenarioTable) throws Exception {
        List<String> scenarioList = scenarioTable.asList();

        ExecutorService executor = Executors.newFixedThreadPool(scenarioList.size());

        for (String scenario : scenarioList) {
            Runnable worker = new ScenarioRunner(scenario);
            executor.execute(worker);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    class ScenarioRunner implements Runnable {
        private String scenario;

        public ScenarioRunner(String scenario) {
            this.scenario = scenario;
        }

        @Override
        public void run() {
            // Burada senaryoyu çalıştırabilirsiniz
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try {
                runScenarioFor(scenario, classLoader);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



    @Given("select product randomly")
    public void selectProductRandomly() throws Exception {
        try {


        WebElement product_list = uiHelper.getWebElement("//ul[@id='1']","xpath");
        List<WebElement> products = product_list.findElements(By.className("productListContent-zAP0Y5msy8OHn5z7T_K_"));
        Random random = new Random();
        WebElement selectedProduct = products.get(random.nextInt(products.size()));
        uiHelper.clickElement(selectedProduct);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Given("verify page load")
    public void verifyPageLoad() throws Exception {
        try {
            waitHelper.verifyPageLoad(driverHelper.getDriver(), 30);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Given("verify evaluations")
    public void verifyEvaluations() throws Exception {
        try {
            WebElement yorumListesi = uiHelper.getWebElement("//div[@class='paginationContentHolder']", "xpath");
            List<WebElement> yorumlar = yorumListesi.findElements(By.className("hermes-ReviewCard-module-dY_oaYMIo0DJcUiSeaVW"));
            if (!yorumlar.isEmpty()) {
                System.out.println("Evaluations are visible.");
                uiBaseStepDefs.elementAction("click", "sirala");
                uiBaseStepDefs.elementAction("click", "enYeniDeg");
                uiBaseStepDefs.elementAction("click", "thumbsUp");
                uiBaseStepDefs.elementAction("isDisplay", "like");
            } else {
                System.out.println("No evaluations found.");  // Element bulunamazsa bu mesajı yazdır
            }
        } catch (Exception e) {
            System.out.println("An error occurred while verifying evaluations.");  // Herhangi bir hata durumunda bu mesajı yazdır
            throw e;
        }
    }
    private By getLocatorByType(String method, String locator) {
        switch (method.toLowerCase()) {
            case "id":
                return By.id(locator);
            case "xpath":
                return By.xpath(locator);
            case "css":
                return By.cssSelector(locator);
            case "name":
                return By.name(locator);
            case "classname":
                return By.className(locator);
            case "linktext":
                return By.linkText(locator);
            case "partiallinktext":
                return By.partialLinkText(locator);
            case "tagname":
                return By.tagName(locator);
            default:
                throw new IllegalArgumentException("Unsupported locator type: " + method);
        }
    }

    @Given("click if {} exist")
    public boolean clickIfElementExists(String elementName) {
        WebElement element;
        String pageName;
        String locator = null;
        String method;

        try {
            pageName = uiHelper.getPageName();
            locator = locatorHelper.getElementLocatorWithJson(pageName, elementName);
            method = locatorHelper.getElementMethodWithJson(pageName, elementName);
            By by = getLocatorByType(method, locator);
            element = driverHelper.getDriver().findElement(by);

            if (element.isDisplayed() && element.isEnabled()) {
                element.click();
                logger.info("Clicked on the element: " + element);
                return true;
            } else {
                logger.info("Element found but not visible or not enabled.");
                return false;
            }
        } catch (NoSuchElementException e) {
            logger.info("Element does not exist: " + locator);  // Log as info, no error thrown
            return false;
        } catch (Exception e) {
            logger.error("An error occurred in clickIfElementExists - " + e.getMessage(), e);
            return false;  // If you don't want to stop the test, just log the error and return false
        }
    }

    @Given("get and add datastore text element {} with keyName {}")
    public void addDataStore(String elementName, String keyName) throws Exception {
        String value;
        String locator = null;
        String method;
        String pageName = uiHelper.getPageName();;
        try {
            locator = locatorHelper.getElementLocatorWithJson(pageName, elementName);
            method = locatorHelper.getElementMethodWithJson(pageName, elementName);
            WebElement element = uiHelper.getWebElement(locator,method);
            String detailPrice = element.getText();
            iniHelper.writeIniFile(keyName,detailPrice);
        } catch (Exception e) {
            logger.error("\t The method causing the error : UiHelper - addDataStore." + elementName);
            throw new Exception(e.getMessage());
        }
    }
    @When("verify with datastore key {}, element data {}")
    public void verifyWithDatastore(String key, String elementName) throws Exception {
        String locator = null;
        String method;
        String pageName = uiHelper.getPageName();;
        try {
            locator = locatorHelper.getElementLocatorWithJson(pageName, elementName);
            method = locatorHelper.getElementMethodWithJson(pageName, elementName);
            WebElement element = uiHelper.getWebElement(locator,method);
            String sepetPrice = element.getText();

            String datastoreValue = iniHelper.getIniFileValue(key);
            System.out.println("DATASTORE VALUE " + datastoreValue);
            System.out.println("CURRENT VALUE " + sepetPrice);
            if (sepetPrice.contains(datastoreValue)) {
            System.out.println("Değerler uyuşuyor.");
            }else {
                throw new Exception("Veri deposundaki değer ile elementin metni uyuşmuyor.");
            }
        } catch (Exception e) {
            logger.error("The method causing the error : UIBaseStepDefs - elementAction");
            throw e;
        }
    }



}


