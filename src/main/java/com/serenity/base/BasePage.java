package com.serenity.base;

import com.serenity.reporting.ReportManager;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

/**
 * BasePage class provides common functionality for all page objects.
 * Extends Serenity's PageObject and adds additional utility methods.
 */
public class BasePage extends PageObject {

    protected static final Logger LOGGER = Logger.getLogger(BasePage.class.getName());
    protected static final int DEFAULT_TIMEOUT = 30;

    /**
     * Wait for element to be visible
     * @param element - WebElementFacade to wait for
     * @param timeoutSeconds - timeout in seconds
     */
    protected void waitForElementVisible(WebElementFacade element, int timeoutSeconds) {
        element.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(timeoutSeconds));
    }

    /**
     * Wait for element to be clickable
     * @param element - WebElementFacade to wait for
     * @param timeoutSeconds - timeout in seconds
     */
    protected void waitForElementClickable(WebElementFacade element, int timeoutSeconds) {
        element.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(timeoutSeconds));
    }

    /**
     * Wait for element to be invisible
     * @param element - WebElementFacade to wait for
     * @param timeoutSeconds - timeout in seconds
     */
    protected void waitForElementInvisible(WebElementFacade element, int timeoutSeconds) {
        element.waitUntilNotVisible().withTimeoutOf(Duration.ofSeconds(timeoutSeconds));
    }

    /**
     * Click element with wait
     * @param element - element to click
     */
    protected void clickElement(WebElementFacade element) {
        waitForElementClickable(element, DEFAULT_TIMEOUT);
        element.click();
        LOGGER.info("Clicked element: " + element.toString());
    }

    /**
     * Click element using JavaScript
     * @param element - element to click
     */
    protected void clickElementJS(WebElementFacade element) {
        waitForElementVisible(element, DEFAULT_TIMEOUT);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
        LOGGER.info("Clicked element using JS: " + element.toString());
    }

    /**
     * Type text into element
     * @param element - element to type into
     * @param text - text to type
     */
    protected void typeText(WebElementFacade element, String text) {
        waitForElementVisible(element, DEFAULT_TIMEOUT);
        element.clear();
        element.type(text);
        LOGGER.info("Typed text: " + text + " into element: " + element.toString());
    }

    /**
     * Get text from element
     * @param element - element to get text from
     * @return element text
     */
    protected String getElementText(WebElementFacade element) {
        waitForElementVisible(element, DEFAULT_TIMEOUT);
        String text = element.getText();
        LOGGER.info("Got text: " + text + " from element: " + element.toString());
        return text;
    }

    /**
     * Check if element is displayed
     * @param element - element to check
     * @return true if displayed
     */
    protected boolean isElementDisplayed(WebElementFacade element) {
        try {
            return element.isCurrentlyVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is enabled
     * @param element - element to check
     * @return true if enabled
     */
    protected boolean isElementEnabled(WebElementFacade element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scroll to element
     * @param element - element to scroll to
     */
    protected void scrollToElement(WebElementFacade element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        LOGGER.info("Scrolled to element: " + element.toString());
    }

    /**
     * Scroll to top of page
     */
    protected void scrollToTop() {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, 0);");
        LOGGER.info("Scrolled to top of page");
    }

    /**
     * Scroll to bottom of page
     */
    protected void scrollToBottom() {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        LOGGER.info("Scrolled to bottom of page");
    }

    /**
     * Wait for page to load
     */
    protected void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(driver -> ((JavascriptExecutor) driver)
            .executeScript("return document.readyState").equals("complete"));
        LOGGER.info("Page loaded completely");
    }

    /**
     * Wait for element by locator
     * @param by - locator
     * @param timeoutSeconds - timeout in seconds
     * @return WebElement
     */
    protected WebElement waitForElement(By by, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Wait for elements by locator
     * @param by - locator
     * @param timeoutSeconds - timeout in seconds
     * @return List of WebElements
     */
    protected List<WebElement> waitForElements(By by, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    /**
     * Execute JavaScript
     * @param script - JavaScript to execute
     * @param args - arguments for the script
     * @return script result
     */
    protected Object executeJavaScript(String script, Object... args) {
        return ((JavascriptExecutor) getDriver()).executeScript(script, args);
    }

    /**
     * Get page URL
     * @return current URL
     */
    protected String getPageUrl() {
        return getDriver().getCurrentUrl();
    }

    /**
     * Get page title
     * @return page title
     */
    protected String getPageTitle() {
        return getDriver().getTitle();
    }

    /**
     * Refresh current page
     */
    protected void refreshPage() {
        getDriver().navigate().refresh();
        LOGGER.info("Page refreshed");
    }

    /**
     * Take screenshot with custom name
     * @param screenshotName - name for screenshot
     */
    protected void captureScreenshot(String screenshotName) {
        ReportManager.takeScreenshot(screenshotName);
    }

    /**
     * Record step in report
     * @param stepDescription - step description
     */
    protected void logStep(String stepDescription) {
        ReportManager.recordStep(stepDescription);
        LOGGER.info("Step: " + stepDescription);
    }

    /**
     * Record custom data in report
     * @param title - data title
     * @param content - data content
     */
    protected void logData(String title, String content) {
        ReportManager.recordCustomData(title, content);
    }

    /**
     * Wait for specified time
     * @param milliseconds - time to wait
     */
    protected void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LOGGER.warning("Wait interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Switch to frame
     * @param frameNameOrId - frame name or ID
     */
    protected void switchToFrame(String frameNameOrId) {
        getDriver().switchTo().frame(frameNameOrId);
        LOGGER.info("Switched to frame: " + frameNameOrId);
    }

    /**
     * Switch to default content
     */
    protected void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
        LOGGER.info("Switched to default content");
    }

    /**
     * Accept alert
     */
    protected void acceptAlert() {
        getDriver().switchTo().alert().accept();
        LOGGER.info("Alert accepted");
    }

    /**
     * Dismiss alert
     */
    protected void dismissAlert() {
        getDriver().switchTo().alert().dismiss();
        LOGGER.info("Alert dismissed");
    }

    /**
     * Get alert text
     * @return alert text
     */
    protected String getAlertText() {
        String text = getDriver().switchTo().alert().getText();
        LOGGER.info("Alert text: " + text);
        return text;
    }
}

