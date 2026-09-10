package base;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Actions actions;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        this.actions = new Actions(driver);
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
    	int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                actions.moveToElement(element).perform();
                wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                return;
            } catch (org.openqa.selenium.TimeoutException
                    | org.openqa.selenium.ElementClickInterceptedException
                    | org.openqa.selenium.StaleElementReferenceException e) {
                attempts++;
                if (attempts == maxAttempts) {
                    throw e;
                }
            }
        }
    }

    protected void write(By locator, String value) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected String text(By locator) {
        return find(locator).getText();
    }

    protected void selectByText(By locator, String value) {
        new Select(find(locator)).selectByVisibleText(value);
    }

    protected boolean isSelected(By locator) {
        return find(locator).isSelected();
    }

    protected boolean exists(By locator) {
        return !driver.findElements(locator).isEmpty();
    }
    
    protected boolean clickAndVerifyUrl(By locator, String expectedUrlFragment) {
        click(locator);
        return wait.until(ExpectedConditions.urlContains(expectedUrlFragment));
    }
}
