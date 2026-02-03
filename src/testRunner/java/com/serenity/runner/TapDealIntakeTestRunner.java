package com.serenity.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Dedicated JUnit runner for TAP Deal Intake feature.
 * Isolates TAP-related scenarios from other test runs.
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/tap/TapDealIntake.feature",
        glue = {"com.serenity.stepdefinition.tap"},
        plugin = {"pretty", "json:target/cucumber/tap-deal-intake-report.json", "html:target/cucumber/tap-deal-intake-report.html"},
        tags = "@TAPDealIntake"
)
public class TapDealIntakeTestRunner {
    // No implementation needed; configuration is via annotations.
}
