package com.serenity.stepdefinition;

import com.serenity.pages.LoginPage;
import com.serenity.utilities.SerenityConfigReader;
import io.cucumber.java.en.Given;
import net.serenitybdd.core.Serenity;

public class LoginStepDefinitions {

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
	 * 1. Opens the application URL (from serenity.properties)
	 * 2. Reads credentials from serenity.properties using SerenityConfigReader
	 * 3. Enters username using LoginPage.enterUsername()
	 * 4. Enters password using LoginPage.enterPassword()
	 * 5. Clicks login button using LoginPage.clickLoginButton()
	 * 6. Records login status in Serenity report
	 */
	@Given("The user is logged into the Cloud Platform application")
	public void theUserIsLoggedIntoTheCloudPlatformApplication() {
		// Open the application (URL from serenity.properties: webdriver.base.url)
		getLoginPage().open();

		// Get credentials from serenity.properties using SerenityConfigReader utility
		String username = SerenityConfigReader.getUsername();
		String password = SerenityConfigReader.getPassword();

		// Perform login using LoginPage methods with locators:
		// - id="username" -> enterUsername()
		// - id="password" -> enterPassword()
		// - id="btnLogin" -> clickLoginButton()
		getLoginPage().login(username, password);

		// Validate login confirmation - wait for login form to disappear
		getLoginPage().waitForLoginConfirmation();

		// Verify login was successful
		if (!getLoginPage().isLoginSuccessful()) {
			throw new AssertionError("Login failed - user was not successfully logged in");
		}

		// Record login action in Serenity report
		Serenity.recordReportData()
			.withTitle("Login Action")
			.andContents("User logged in successfully with username: " + username);
	}

	/**
	 * Alternative step with parameterized credentials
	 */
	@Given("The user is logged into the Cloud Platform application with username {string} and password {string}")
	public void theUserIsLoggedInWithCredentials(String username, String password) {
		getLoginPage().open();

		// Use LoginPage methods with the page locators
		getLoginPage().login(username, password);

		// Validate login confirmation - wait for login form to disappear
		getLoginPage().waitForLoginConfirmation();

		// Verify login was successful
		if (!getLoginPage().isLoginSuccessful()) {
			throw new AssertionError("Login failed - user was not successfully logged in");
		}

		Serenity.recordReportData()
			.withTitle("Login Action")
			.andContents("User logged in with username: " + username);
	}
}

