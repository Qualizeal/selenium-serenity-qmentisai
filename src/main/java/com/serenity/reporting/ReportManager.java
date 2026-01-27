package com.serenity.reporting;

import net.serenitybdd.core.Serenity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.serenity.driver.DriverManager;
import com.serenity.utilities.SerenityConfigReader;

/**
 * ReportManager class responsible for report generation, configuration, and custom data management.
 * Handles Serenity report customization and metadata recording.
 */
public class ReportManager {

    private static final Logger LOGGER = Logger.getLogger(ReportManager.class.getName());
    private static final String REPORT_DIR = "target/site/serenity";
    private static final String CUCUMBER_REPORT_DIR = "target/cucumber";
    private static final ThreadLocal<Map<String, Object>> testContext = new ThreadLocal<>();

    /**
     * Initialize test context for the current thread
     */
    public static void initializeTestContext() {
        testContext.set(new HashMap<>());
        LOGGER.info("Test context initialized for thread: " + Thread.currentThread().getName());
    }

    /**
     * Clear test context for the current thread
     */
    public static void clearTestContext() {
        testContext.remove();
        LOGGER.info("Test context cleared for thread: " + Thread.currentThread().getName());
    }

    /**
     * Store data in test context
     * @param key - context key
     * @param value - context value
     */
    public static void setTestContext(String key, Object value) {
        Map<String, Object> context = testContext.get();
        if (context == null) {
            initializeTestContext();
            context = testContext.get();
        }
        context.put(key, value);
    }

    /**
     * Retrieve data from test context
     * @param key - context key
     * @return context value
     */
    public static Object getTestContext(String key) {
        Map<String, Object> context = testContext.get();
        return context != null ? context.get(key) : null;
    }

    /**
     * Record test execution metadata in Serenity report
     */
    public static void recordTestExecutionMetadata() {
        try {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String environment = SerenityConfigReader.getEnvironment();
            String appVersion = SerenityConfigReader.getApplicationVersion();
            String browser = DriverManager.getBrowserName();
            String browserVersion = DriverManager.getBrowserVersion();

            // Record as custom fields
            Serenity.recordReportData()
                .withTitle("Test Execution Details")
                .andContents(
                    "Timestamp: " + timestamp + "\n" +
                    "Environment: " + environment + "\n" +
                    "Application Version: " + appVersion + "\n" +
                    "Browser: " + browser + " " + browserVersion
                );

            LOGGER.info("Test execution metadata recorded: " + environment + ", " + appVersion);
        } catch (Exception e) {
            LOGGER.warning("Failed to record test execution metadata: " + e.getMessage());
        }
    }

    /**
     * Record custom data in Serenity report
     * @param title - data title
     * @param content - data content
     */
    public static void recordCustomData(String title, String content) {
        try {
            Serenity.recordReportData()
                .withTitle(title)
                .andContents(content);
            LOGGER.info("Custom data recorded - Title: " + title);
        } catch (Exception e) {
            LOGGER.warning("Failed to record custom data: " + e.getMessage());
        }
    }

    /**
     * Record custom data with key-value pairs
     * @param title - data title
     * @param data - map of key-value pairs
     */
    public static void recordCustomData(String title, Map<String, String> data) {
        try {
            StringBuilder content = new StringBuilder();
            data.forEach((key, value) -> content.append(key).append(": ").append(value).append("\n"));

            Serenity.recordReportData()
                .withTitle(title)
                .andContents(content.toString());

            LOGGER.info("Custom data recorded - Title: " + title + " with " + data.size() + " entries");
        } catch (Exception e) {
            LOGGER.warning("Failed to record custom data map: " + e.getMessage());
        }
    }

    /**
     * Add a tag to the current test
     * @param tag - tag to add
     */
    public static void addTag(String tag) {
        try {
            // Store tag in session for reference
            Serenity.setSessionVariable("test_tag_" + tag).to(tag);
            LOGGER.info("Tag added: " + tag);
        } catch (Exception e) {
            LOGGER.warning("Failed to add tag: " + e.getMessage());
        }
    }

    /**
     * Set the current test title
     * @param title - test title
     */
    public static void setTestTitle(String title) {
        try {
            Serenity.setSessionVariable("test_title").to(title);
            recordCustomData("Test Title", title);
            LOGGER.info("Test title set: " + title);
        } catch (Exception e) {
            LOGGER.warning("Failed to set test title: " + e.getMessage());
        }
    }

    /**
     * Record a step with description
     * @param description - step description
     */
    public static void recordStep(String description) {
        try {
            Serenity.recordReportData()
                .withTitle("Step")
                .andContents(description);
            LOGGER.info("Step recorded: " + description);
        } catch (Exception e) {
            LOGGER.warning("Failed to record step: " + e.getMessage());
        }
    }

    /**
     * Take a screenshot with custom name
     * @param screenshotName - name for the screenshot
     */
    public static void takeScreenshot(String screenshotName) {
        try {
            Serenity.takeScreenshot();
            LOGGER.info("Screenshot taken: " + screenshotName);
        } catch (Exception e) {
            LOGGER.warning("Failed to take screenshot: " + e.getMessage());
        }
    }

    /**
     * Record test failure details
     * @param testName - name of the failed test
     * @param errorMessage - error message
     * @param stackTrace - stack trace
     */
    public static void recordTestFailure(String testName, String errorMessage, String stackTrace) {
        try {
            Map<String, String> failureData = new HashMap<>();
            failureData.put("Test Name", testName);
            failureData.put("Error Message", errorMessage);
            failureData.put("Stack Trace", stackTrace);
            failureData.put("Timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            recordCustomData("Test Failure Details", failureData);
            takeScreenshot("Failure_" + testName);

            LOGGER.severe("Test failure recorded: " + testName);
        } catch (Exception e) {
            LOGGER.warning("Failed to record test failure: " + e.getMessage());
        }
    }

    /**
     * Get the Serenity report directory
     * @return report directory path
     */
    public static String getReportDirectory() {
        return REPORT_DIR;
    }

    /**
     * Get the Cucumber report directory
     * @return cucumber report directory path
     */
    public static String getCucumberReportDirectory() {
        return CUCUMBER_REPORT_DIR;
    }

    /**
     * Check if report directory exists
     * @return true if report directory exists
     */
    public static boolean reportDirectoryExists() {
        File reportDir = new File(REPORT_DIR);
        return reportDir.exists() && reportDir.isDirectory();
    }

    /**
     * Create report directory if it doesn't exist
     */
    public static void ensureReportDirectoryExists() {
        try {
            Path reportPath = Paths.get(REPORT_DIR);
            if (!Files.exists(reportPath)) {
                Files.createDirectories(reportPath);
                LOGGER.info("Report directory created: " + REPORT_DIR);
            }
        } catch (IOException e) {
            LOGGER.warning("Failed to create report directory: " + e.getMessage());
        }
    }

    /**
     * Generate Serenity aggregate report
     * Note: Serenity generates reports automatically after test execution
     */
    public static void generateAggregateReport() {
        try {
            LOGGER.info("Serenity aggregate report will be generated automatically after test execution");
            LOGGER.info("Report location: " + new File(REPORT_DIR).getAbsolutePath());
        } catch (Exception e) {
            LOGGER.warning("Failed to log report information: " + e.getMessage());
        }
    }

    /**
     * Clean old reports
     * @param daysToKeep - number of days to keep reports
     */
    public static void cleanOldReports(int daysToKeep) {
        try {
            LOGGER.info("Cleaning reports older than " + daysToKeep + " days...");
            // Implementation for cleaning old reports based on timestamp
            // This can be customized based on your requirements
        } catch (Exception e) {
            LOGGER.warning("Failed to clean old reports: " + e.getMessage());
        }
    }

    /**
     * Record environment information in report
     */
    public static void recordEnvironmentInfo() {
        try {
            Map<String, String> envInfo = new HashMap<>();
            envInfo.put("OS", System.getProperty("os.name"));
            envInfo.put("OS Version", System.getProperty("os.version"));
            envInfo.put("OS Architecture", System.getProperty("os.arch"));
            envInfo.put("Java Version", System.getProperty("java.version"));
            envInfo.put("User", System.getProperty("user.name"));
            envInfo.put("Working Directory", System.getProperty("user.dir"));
            envInfo.put("Environment", SerenityConfigReader.getEnvironment());
            envInfo.put("Application Version", SerenityConfigReader.getApplicationVersion());

            recordCustomData("Environment Information", envInfo);
            LOGGER.info("Environment information recorded");
        } catch (Exception e) {
            LOGGER.warning("Failed to record environment info: " + e.getMessage());
        }
    }

    /**
     * Print report summary to console
     */
    public static void printReportSummary() {
        LOGGER.info("========================================");
        LOGGER.info("Test Execution Summary");
        LOGGER.info("========================================");
        LOGGER.info("Project: " + SerenityConfigReader.getProjectName());
        LOGGER.info("Environment: " + SerenityConfigReader.getEnvironment());
        LOGGER.info("Application Version: " + SerenityConfigReader.getApplicationVersion());
        LOGGER.info("Report Location: " + new File(REPORT_DIR).getAbsolutePath());
        LOGGER.info("========================================");
    }
}

