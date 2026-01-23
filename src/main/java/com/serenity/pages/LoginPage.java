package com.serenity.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import java.time.Duration;

public class LoginPage extends PageObject {

	@FindBy(id = "username")
	private WebElementFacade username;

	@FindBy(id = "password")
	private WebElementFacade password;

	@FindBy(id = "btnLogin")
	private WebElementFacade btnLogin;

	/**
	 * Enter username
	 * @param userName - username value
	 */
	public void enterUsername(String userName) {
		username.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(60));
		username.clear();
		username.type(userName);
	}

	/**
	 * Enter password
	 * @param pwd - password value
	 */
	public void enterPassword(String pwd) {
		password.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(60));
		password.clear();
		password.type(pwd);
	}

	/**
	 * Click on Login button
	 */
	public void clickLoginButton() {
		btnLogin.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(60));
		btnLogin.click();
	}

	/**
	 * Perform login with username and password
	 * @param userName - username value
	 * @param pwd - password value
	 */
	public void login(String userName, String pwd) {
		enterUsername(userName);
		enterPassword(pwd);
		clickLoginButton();
	}

	/**
	 * Check if login button is visible
	 * @return true if login button is visible
	 */
	public boolean isLoginButtonVisible() {
		return btnLogin.isVisible();
	}

	/**
	 * Check if username field is visible
	 * @return true if username field is visible
	 */
	public boolean isUsernameFieldVisible() {
		return username.isVisible();
	}

	/**
	 * Check if password field is visible
	 * @return true if password field is visible
	 */
	public boolean isPasswordFieldVisible() {
		return password.isVisible();
	}

	/**
	 * Verify login was successful by checking if login form is no longer visible
	 * @return true if login was successful (login button not visible)
	 */
	public boolean isLoginSuccessful() {
		// Wait a moment for page transition after login
		waitABit(2000);
		// Login is successful if the login button is no longer visible
		return !btnLogin.isCurrentlyVisible();
	}

	/**
	 * Wait for login confirmation - ensures login form disappears
	 */
	public void waitForLoginConfirmation() {
		btnLogin.waitUntilNotVisible().withTimeoutOf(Duration.ofSeconds(30));
	}
}

