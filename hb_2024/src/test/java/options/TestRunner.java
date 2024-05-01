package options;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

/**
 * TestNG ile Cucumber testlerini çalıştırmak için kullanılan runner sınıfı.
 */
@CucumberOptions(
        features = {"src/test/resources/features"},
        publish = true,
        glue = {"stepdefs"},
        plugin = {   "pretty", // Terminalde daha okunaklı çıktı sağlar.
                "html:target/cucumber-reports/html", // HTML raporunu target/cucumber-reports/html dizinine kaydeder.
                "json:target/cucumber-reports/cucumber.json", // JSON raporunu target/cucumber-reports dizinine kaydeder.
                "junit:target/cucumber-reports/cucumber.xml" // JUnit XML raporunu target/cucumber-reports dizinine kaydeder.
                 },
        tags = "@test"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Test süiti başlamadan önce çalışan metot.
     */
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Test Suite Started ***********************************************************************");
    }

    /**
     * Her bir test tamamlandığında çalışan metot.
     */
    @AfterTest
    public void afterTest() {
        System.out.println("Test Case Finished ***********************************************************************");
    }

    /**
     * Test süiti tamamlandığında çalışan metot.
     */
    @AfterSuite
    public void afterSuite() {
        System.out.println("Test Suite Finished ***********************************************************************");
    }
}
