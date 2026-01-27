package com.serenity.stepdefinition;

import com.serenity.pages.SettingsPage;
import com.serenity.reporting.ReportManager;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.Serenity;

/**
 * Step definitions for SettingsPage functionality.
 */
public class SettingsPageStepDefinitions {

	/**
	 * Get or create SettingsPage instance using Serenity session for thread-safety
	 * @return SettingsPage instance
	 */
	private SettingsPage getSettingsPage() {
		// Use Serenity session to store page object - thread-safe for parallel execution
		SettingsPage settingsPage = Serenity.sessionVariableCalled("settingsPage");
		if (settingsPage == null) {
			settingsPage = new SettingsPage();
			settingsPage.setDriver(Serenity.getDriver());
			Serenity.setSessionVariable("settingsPage").to(settingsPage);
		}
		return settingsPage;
	}

	/**
	 * Step: Then Navigate to "Investment Policy Templates"
	 *
	 * This step navigates to the specified section in Settings.
	 */
	@Then("Navigate to Investment Policy Templates on Settings page")
	public void navigateToSection() {
		SettingsPage settingsPage = getSettingsPage();
		
		settingsPage.clickInvestmentPolicyTemplatesLink();

		// Record action in Serenity report
		ReportManager.recordCustomData("Navigate to Section", "Navigated to Investment Policy Templates on Settings page");
		ReportManager.recordStep("Navigated to Investment Policy Templates on Settings page");
	}

	/**
	 * Step: Then Select the template "QZ Policy Test Template"
	 *
	 * This step selects the specified template from the list.
	 */
	@Then("Select the template {string}")
	public void selectTheTemplate(String templateName) {
		SettingsPage settingsPage = getSettingsPage();
		
		settingsPage.findAndClickTemplate(templateName);

		// Record action in Serenity report
		ReportManager.recordCustomData("Select Template", "Selected template: " + templateName);
		ReportManager.recordStep("Selected template: " + templateName);
	}
}
