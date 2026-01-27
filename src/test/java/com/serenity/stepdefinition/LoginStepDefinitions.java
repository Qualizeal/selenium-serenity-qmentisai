package com.serenity.stepdefinition;

import com.serenity.pages.LoginPage;
import com.serenity.utilities.SerenityConfigReader;
import com.serenity.reporting.ReportManager;
import io.cucumber.java.en.Given;
import net.serenitybdd.core.Serenity;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for Login functionality.
 */
public class LoginStepDefinitions {

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
	 * Step: Given The user is logged into the Cloud Platform application
	 *
	 * This step:
	 * 1. Opens the application URL (from configuration)
	 * 2. Reads credentials from configuration
	 * 3. Enters username and password
	 * 4. Clicks login button
	 * 5. Validates successful login
	 * 6. Records login details in report
	 */
	@Given("The user is logged into the Cloud Platform application")
	public void theUserIsLoggedIntoTheCloudPlatformApplication() {
		LoginPage loginPage = getLoginPage();
		
		// Open the application (URL from configuration)
		loginPage.open();

		// Get credentials from configuration
		String username = SerenityConfigReader.getUsername();
		String password = SerenityConfigReader.getPassword();
		
		// Perform login
		loginPage.login(username, password);

		// Wait for login confirmation (login form disappears)
		loginPage.waitForLoginConfirmation();

		// Validate login was successful
		assertThat(loginPage.isLoginSuccessful())
			.as("User should be successfully logged in")
			.withFailMessage("Login failed - user was not successfully logged in. URL: " + loginPage.getDriver().getCurrentUrl())
			.isTrue();

		// Record login action in Serenity report
		ReportManager.recordCustomData("Login Action", "User logged in successfully with username: " + username);
		ReportManager.recordStep("User successfully logged into the Cloud Platform application");
	}
}
