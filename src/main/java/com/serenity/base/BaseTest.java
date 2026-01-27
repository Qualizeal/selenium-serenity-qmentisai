package com.serenity.base;

import com.serenity.driver.DriverManager;
import com.serenity.reporting.ReportManager;
import com.serenity.utilities.SerenityConfigReader;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

/**
 * BaseTest class provides common functionality for all test classes.
 * Includes driver management, page object initialization, and reporting utilities.
 */
public class BaseTest {

    protected static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());

    /**
     * Get WebDriver instance
     * @return WebDriver instance
     */
    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    /**
     * Initialize a page object with the current driver
     * @param pageClass - Page class to initialize
     * @param <T> - Page type
     * @return Page instance
     */
    protected <T extends PageObject> T initPage(Class<T> pageClass) {
        try {
            T page = pageClass.getDeclaredConstructor().newInstance();
            page.setDriver(getDriver());
            LOGGER.info("Initialized page: " + pageClass.getSimpleName());
            return page;
        } catch (Exception e) {
            LOGGER.severe("Failed to initialize page: " + pageClass.getSimpleName());
            throw new RuntimeException("Failed to initialize page: " + pageClass.getSimpleName(), e);
        }
    }

    /**
     * Get or create a page object from Serenity session
     * @param pageClass - Page class
     * @param sessionKey - Session key for storing the page
     * @param <T> - Page type
     * @return Page instance
     */
    protected <T extends PageObject> T getPage(Class<T> pageClass, String sessionKey) {
        T page = Serenity.sessionVariableCalled(sessionKey);
        if (page == null) {
            page = initPage(pageClass);
            Serenity.setSessionVariable(sessionKey).to(page);
        }
        return page;
    }

    /**
     * Navigate to base URL
     */
    protected void navigateToBaseUrl() {
        String baseUrl = SerenityConfigReader.getBaseUrl();
        LOGGER.info("Navigating to base URL: " + baseUrl);
        getDriver().get(baseUrl);
    }

    /**
     * Wait for specified milliseconds
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
     * Record step in report
     * @param stepDescription - description of the step
     */
    protected void recordStep(String stepDescription) {
        ReportManager.recordStep(stepDescription);
    }

    /**
     * Take screenshot
     * @param screenshotName - name for the screenshot
     */
    protected void takeScreenshot(String screenshotName) {
        ReportManager.takeScreenshot(screenshotName);
    }

    /**
     * Record custom data in report
     * @param title - data title
     * @param content - data content
     */
    protected void recordData(String title, String content) {
        ReportManager.recordCustomData(title, content);
    }

    /**
     * Get current URL
     * @return current URL
     */
    protected String getCurrentUrl() {
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
     * Refresh the page
     */
    protected void refreshPage() {
        LOGGER.info("Refreshing page");
        getDriver().navigate().refresh();
    }

    /**
     * Navigate back
     */
    protected void navigateBack() {
        LOGGER.info("Navigating back");
        getDriver().navigate().back();
    }

    /**
     * Navigate forward
     */
    protected void navigateForward() {
        LOGGER.info("Navigating forward");
        getDriver().navigate().forward();
    }

    /**
     * Check if driver is initialized
     * @return true if driver is initialized
     */
    protected boolean isDriverReady() {
        return DriverManager.isDriverInitialized();
    }

    /**
     * Get browser information
     * @return browser info string
     */
    protected String getBrowserInfo() {
        return DriverManager.getBrowserName() + " " + DriverManager.getBrowserVersion();
    }

    /**
     * Log information message
     * @param message - message to log
     */
    protected void logInfo(String message) {
        LOGGER.info(message);
    }

    /**
     * Log warning message
     * @param message - message to log
     */
    protected void logWarning(String message) {
        LOGGER.warning(message);
    }

    /**
     * Log error message
     * @param message - message to log
     */
    protected void logError(String message) {
        LOGGER.severe(message);
    }
}

