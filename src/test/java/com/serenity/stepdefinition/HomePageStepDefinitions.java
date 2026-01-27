package com.serenity.stepdefinition;

import com.serenity.pages.HomePage;
import com.serenity.pages.LoginPage;
import com.serenity.reporting.ReportManager;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.Serenity;

/**
 * Step definitions for HomePage functionality.
 */
public class HomePageStepDefinitions {

	/**
	 * Get or create HomePage instance using Serenity session for thread-safety
	 * @return HomePage instance
	 */
	private HomePage getHomePage() {
		// Use Serenity session to store page object - thread-safe for parallel execution
		HomePage homePage = Serenity.sessionVariableCalled("homePage");
		if (homePage == null) {
			homePage = new HomePage();
			homePage.setDriver(Serenity.getDriver());
			Serenity.setSessionVariable("homePage").to(homePage);
		}
		return homePage;
	}

	/**
	 * Get or create LoginPage instance using Serenity session for thread-safety
	 * @return LoginPage instance
	 */
	private LoginPage getLoginPage() {
		// Use Serenity session to store page object - thread-safe for parallel execution
		LoginPage loginPage = Serenity.sessionVariableCalled("loginPage");
		if (loginPage == null) {
			loginPage = new LoginPage();
			loginPage.setDriver(Serenity.getDriver());
			Serenity.setSessionVariable("loginPage").to(loginPage);
		}
		return loginPage;
	}

	/**
	 * Step: Then Launch "Fiduciary Focus Toolkit™"
	 *
	 * This step launches the specified application.
	 */
	@Then("Launch {string}")
	public void launchApplication(String applicationName) {
		LoginPage loginPage = getLoginPage();
		
		loginPage.clickAppLaunchButton();

		// Record action in Serenity report
		ReportManager.recordCustomData("Launch Application", "Launched application: " + applicationName);
		ReportManager.recordStep("Launched " + applicationName);
	}

	/**
	 * Step: Then Click on Settings from top right corner
	 *
	 * This step clicks on the Settings button from the top right corner.
	 */
	@Then("Click on Settings from top right corner")
	public void clickOnSettingsFromTopRightCorner() {
		HomePage homePage = getHomePage();
		
		homePage.navigateToSettings();

		// Record action in Serenity report
		ReportManager.recordStep("Clicked on Settings from top right corner");
	}
}
