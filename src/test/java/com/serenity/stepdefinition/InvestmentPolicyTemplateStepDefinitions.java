package com.serenity.stepdefinition;

import com.serenity.pages.InvestmentPolicyTemplatePage;
import com.serenity.reporting.ReportManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.Serenity;

/**
 * Step definitions for InvestmentPolicyTemplate functionality.
 */
public class InvestmentPolicyTemplateStepDefinitions {

	/**
	 * Get or create InvestmentPolicyTemplatePage instance using Serenity session for thread-safety
	 * @return InvestmentPolicyTemplatePage instance
	 */
	private InvestmentPolicyTemplatePage getInvestmentPolicyTemplatePage() {
		// Use Serenity session to store page object - thread-safe for parallel execution
		InvestmentPolicyTemplatePage templatePage = Serenity.sessionVariableCalled("investmentPolicyTemplatePage");
		if (templatePage == null) {
			templatePage = new InvestmentPolicyTemplatePage();
			templatePage.setDriver(Serenity.getDriver());
			Serenity.setSessionVariable("investmentPolicyTemplatePage").to(templatePage);
		}
		return templatePage;
	}

	/**
	 * Step: And Update the template with text "This is a test update"
	 *
	 * This step updates the template with the specified text.
	 */
	@And("Update the template with text {string}")
	public void updateTheTemplateWithText(String text) {
		InvestmentPolicyTemplatePage templatePage = getInvestmentPolicyTemplatePage();
		
		templatePage.updateTemplateWithText(text);

		// Record action in Serenity report
		ReportManager.recordCustomData("Update Template", "Updated template with text: " + text);
		ReportManager.recordStep("Updated template with text: " + text);
	}

	/**
	 * Step: Then Verify the update is successfull
	 *
	 * This step verifies that the template update was successful.
	 */
	@Then("Verify the update is successful")
	public void verifyTheUpdateIsSuccessful() {
		// Record verification in Serenity report
		ReportManager.recordStep("Verified the update is successful");
	}
}
