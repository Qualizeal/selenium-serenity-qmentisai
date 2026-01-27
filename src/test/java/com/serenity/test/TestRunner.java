package com.serenity.test;

import com.serenity.utilities.EnvironmentLoader;
import com.serenity.utilities.SerenityConfigReader;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

/**
 * TestRunner - Entry point for Cucumber test execution with Serenity BDD
 * 
 * Configuration is loaded from serenity.properties with sensible defaults.
 * System properties and environment variables can override values.
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		plugin = {
			"pretty:target/cucumber/cucumber.txt", 
			"html:target/cucumber/cucumber-html-report",
			"json:target/cucumber/cucumber.json"
		},
		features = "src/test/resources/features",
		glue = {"com.serenity.stepdefinition", "com.serenity.hooks"},
		monochrome = true,
		tags = "@ICSMFRS360-60345"
)
public class TestRunner {

	/**
	 * Static initializer - runs once before any tests
	 */
	static {
		logConfiguration();
	}
	
	/**
	 * Log configuration details
	 */
	private static void logConfiguration() {
		System.out.println("==================================================");
		System.out.println("SERENITY TEST CONFIGURATION");
		System.out.println("==================================================");
		System.out.println("Environment: " + SerenityConfigReader.getEnvironment());
		System.out.println("Running in Pipeline: " + EnvironmentLoader.isRunningInPipeline());
		System.out.println("Base URL: " + SerenityConfigReader.getBaseUrl());
		System.out.println("Browser: " + SerenityConfigReader.getBrowser());
		System.out.println("==================================================");
	}
}
