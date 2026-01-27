package com.serenity.hooks;

import com.serenity.driver.DriverManager;
import com.serenity.reporting.ReportManager;
import com.serenity.utilities.SerenityConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import net.serenitybdd.core.Serenity;

import java.util.logging.Logger;

/**
 * TestHooks - Cucumber hooks for test lifecycle management
 * Handles setup, teardown, and reporting for each scenario
 */
public class TestHooks {

    private static final Logger LOGGER = Logger.getLogger(TestHooks.class.getName());
    private static int scenarioCount = 0;

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        scenarioCount++;
        String scenarioName = scenario.getName();
        
        LOGGER.info("========================================");
        LOGGER.info("STARTING SCENARIO #" + scenarioCount + ": " + scenarioName);
        LOGGER.info("========================================");
        
        try {
            ReportManager.initializeTestContext();
            ReportManager.setTestContext("scenario_start_time", System.currentTimeMillis());
            ReportManager.setTestContext("scenario_name", scenarioName);
            ReportManager.recordTestExecutionMetadata();
            
            if (scenarioCount == 1) {
                ReportManager.recordEnvironmentInfo();
            }
            
            ReportManager.setTestTitle(scenarioName);
            
            scenario.getSourceTagNames().forEach(tag -> {
                ReportManager.addTag(tag.replace("@", ""));
                LOGGER.info("Tag added: " + tag);
            });
            
            LOGGER.info("Scenario setup completed successfully");
            
        } catch (Exception e) {
            LOGGER.severe("Error in beforeScenario hook: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Before(order = 2)
    public void initializeDriver(Scenario scenario) {
        try {
            LOGGER.info("Initializing WebDriver...");
            
            if (DriverManager.isDriverInitialized()) {
                LOGGER.info("WebDriver already initialized");
            } else {
                LOGGER.info("WebDriver will be initialized on first use");
            }
            
        } catch (Exception e) {
            LOGGER.severe("Error initializing WebDriver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        try {
            ReportManager.setTestContext("step_start_time", System.currentTimeMillis());
        } catch (Exception e) {
            LOGGER.warning("Error in beforeStep hook: " + e.getMessage());
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        try {
            Long stepStartTime = (Long) ReportManager.getTestContext("step_start_time");
            if (stepStartTime != null) {
                long duration = System.currentTimeMillis() - stepStartTime;
                LOGGER.info("Step execution time: " + duration + "ms");
            }
            
            String screenshotMode = SerenityConfigReader.getProperty("serenity.take.screenshots", "AFTER_EACH_STEP");
            if ("AFTER_EACH_STEP".equals(screenshotMode) || "FOR_EACH_ACTION".equals(screenshotMode)) {
                Serenity.takeScreenshot();
            }
            
        } catch (Exception e) {
            LOGGER.warning("Error in afterStep hook: " + e.getMessage());
        }
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        String status = scenario.getStatus().toString();
        
        try {
            Long scenarioStartTime = (Long) ReportManager.getTestContext("scenario_start_time");
            if (scenarioStartTime != null) {
                long duration = System.currentTimeMillis() - scenarioStartTime;
                LOGGER.info("Scenario execution time: " + duration + "ms");
                ReportManager.recordCustomData("Scenario Duration", duration + "ms");
            }
            
            if (scenario.isFailed()) {
                LOGGER.severe("SCENARIO FAILED: " + scenarioName);
                ReportManager.takeScreenshot("Failure_" + scenarioName.replaceAll("[^a-zA-Z0-9]", "_"));
                String errorMessage = scenario.getStatus().toString();
                ReportManager.recordTestFailure(scenarioName, errorMessage, "Check Serenity report for details");
            } else if (scenario.getStatus().toString().equals("PASSED")) {
                LOGGER.info("SCENARIO PASSED: " + scenarioName);
            } else {
                LOGGER.warning("SCENARIO STATUS: " + status + " - " + scenarioName);
            }
            
            ReportManager.clearTestContext();
            
        } catch (Exception e) {
            LOGGER.severe("Error in afterScenario hook: " + e.getMessage());
            e.printStackTrace();
        } finally {
            LOGGER.info("========================================");
            LOGGER.info("COMPLETED SCENARIO: " + scenarioName + " [" + status + "]");
            LOGGER.info("========================================");
        }
    }

    @After(order = 2)
    public void cleanupDriver(Scenario scenario) {
        try {
            if (DriverManager.isDriverInitialized()) {
                LOGGER.info("WebDriver cleanup - Serenity will handle driver quit");
            }
        } catch (Exception e) {
            LOGGER.warning("Error in driver cleanup: " + e.getMessage());
        }
    }

    public static void afterAllScenarios() {
        try {
            LOGGER.info("========================================");
            LOGGER.info("ALL SCENARIOS COMPLETED");
            LOGGER.info("Total scenarios executed: " + scenarioCount);
            LOGGER.info("========================================");
            
            ReportManager.printReportSummary();
            
        } catch (Exception e) {
            LOGGER.severe("Error in afterAllScenarios: " + e.getMessage());
        }
    }
}
