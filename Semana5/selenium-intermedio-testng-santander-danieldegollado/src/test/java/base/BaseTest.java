package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import pages.DashboardPage;
import utils.DriverFactory;

public abstract class BaseTest {
    protected WebDriver driver;
    protected DashboardPage dashboardPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.createChromeDriver();
        driver.get("https://www.santander.com.mx/");
        dashboardPage = new DashboardPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
