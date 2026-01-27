package com.serenity.pages;

import com.serenity.base.BasePage;
import com.serenity.exceptions.ElementException;
import com.serenity.exceptions.PageException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import java.time.Duration;

/**
 * LoginPage represents the login page of the application.
 * Contains all locators and actions related to login functionality.
 */
public class LoginPage extends BasePage {

	// Locators
	@FindBy(id = "username")
	private WebElementFacade usernameField;

	@FindBy(id = "password")
	private WebElementFacade passwordField;

	@FindBy(id = "btnLogin")
	private WebElementFacade loginButton;
@FindBy(xpath = "(//div//span[text()='Fiduciary Focus Toolkit™'])[last()]//following::div/a[@id='launch_']")
private WebElementFacade appLaunchButton;	
// Post-login success indicators
	private static final By DASHBOARD_INDICATOR = By.cssSelector("[class*='dashboard'], [id*='dashboard']");
	private static final By USER_PROFILE = By.cssSelector("[class*='user-profile'], [class*='user-menu']");
	private static final By LOGOUT_BUTTON = By.cssSelector("[class*='logout'], button[title*='Logout']");

	/**
	 * Enter username into the username field
	 * @param userName - username value
	 * @throws ElementException if username field is not interactable
	 */
	public void enterUsername(String userName) {
		try {
			usernameField.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(30));
			usernameField.clear();
			usernameField.type(userName);
			logStep("Entered username: " + userName);
		} catch (Exception e) {
			throw new ElementException("Failed to enter username: " + userName, e);
		}
	}

	/**
	 * Enter password into the password field
	 * @param pwd - password value
	 * @throws ElementException if password field is not interactable
	 */
	public void enterPassword(String pwd) {
		try {
			passwordField.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(30));
			passwordField.clear();
			passwordField.type(pwd);
			logStep("Entered password");
		} catch (Exception e) {
			throw new ElementException("Failed to enter password", e);
		}
	}

	/**
	 * Click on Login button
	 * @throws ElementException if login button is not clickable
	 */
	public void clickLoginButton() {
		try {
			loginButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(30));
			loginButton.click();
			logStep("Clicked login button");
		} catch (Exception e) {
			throw new ElementException("Failed to click login button", e);
		}
	}

	/**
	 * Perform complete login with username and password
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
		try {
			return loginButton.isVisible();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Check if username field is visible
	 * @return true if username field is visible
	 */
	public boolean isUsernameFieldVisible() {
		try {
			return usernameField.isVisible();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Check if password field is visible
	 * @return true if password field is visible
	 */
	public boolean isPasswordFieldVisible() {
		try {
			return passwordField.isVisible();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wait for login form to disappear (indicating successful login)
	 * @throws PageException if login form doesn't disappear within timeout
	 */
	public void waitForLoginConfirmation() {
		try {
			loginButton.waitUntilNotVisible().withTimeoutOf(Duration.ofSeconds(30));
			logStep("Login form disappeared - login successful");
		} catch (Exception e) {
			throw new PageException("Login form did not disappear within timeout - login may have failed", e);
		}
	}

	/**
	 * Verify login was successful by checking multiple positive indicators
	 * This is more robust than just checking if login button is gone
	 * @return true if any post-login element is visible
	 */
	public boolean isLoginSuccessful() {
		try {
			// Wait for login button to disappear
			loginButton.waitUntilNotVisible().withTimeoutOf(Duration.ofSeconds(10));
			
			// Positive validation: check for dashboard or user profile elements
			// At least one should be present after successful login
			boolean hasPostLoginElements = isElementPresent(DASHBOARD_INDICATOR, 5) ||
										   isElementPresent(USER_PROFILE, 5) ||
										   isElementPresent(LOGOUT_BUTTON, 5);
			
			// Also verify URL changed from login page
			boolean urlChanged = !getPageUrl().contains("/login");
			
			logStep("Login validation - Post-login elements present: " + hasPostLoginElements + 
					", URL changed: " + urlChanged);
			
			return hasPostLoginElements || urlChanged;
			
		} catch (Exception e) {
			LOGGER.warning("Login success validation failed: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Check if a specific element is present on the page
	 * @param locator - By locator
	 * @param timeoutSeconds - timeout in seconds
	 * @return true if element is present
	 */
	private boolean isElementPresent(By locator, int timeoutSeconds) {
		try {
			waitForElement(locator, timeoutSeconds);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verify login page is displayed
	 * @return true if on login page
	 */
	public boolean isOnLoginPage() {
		return isLoginButtonVisible() && isUsernameFieldVisible() && isPasswordFieldVisible();
	}

	/**
	 * Get error message displayed on login page (if any)
	 * Common error message selectors
	 * @return error message text or empty string
	 */
	public String getLoginErrorMessage() {
		try {
			// Try multiple common error message selectors
			String[] errorSelectors = {
				".error-message", ".alert-danger", ".error", "[class*='error']",
				".invalid-feedback", "#error", "[role='alert']"
			};
			
			for (String selector : errorSelectors) {
				try {
					WebElementFacade errorElement = find(By.cssSelector(selector));
					if (errorElement.isVisible()) {
						String errorText = errorElement.getText();
						logStep("Found error message: " + errorText);
						return errorText;
					}
				} catch (Exception ignored) {
					// Try next selector
				}
			}
			
			return "";
		} catch (Exception e) {
			LOGGER.warning("Could not retrieve error message: " + e.getMessage());
			return "";
		}
	}

	/**
	 * Check if login failed with error message
	 * @return true if error message is displayed
	 */
	public boolean hasLoginError() {
		String errorMessage = getLoginErrorMessage();
		return errorMessage != null && !errorMessage.isEmpty();
	}

	/**
	 * Clear login form fields
	 */
	public void clearLoginForm() {
		try {
			usernameField.clear();
			passwordField.clear();
			logStep("Cleared login form");
		} catch (Exception e) {
			LOGGER.warning("Failed to clear login form: " + e.getMessage());
		}
	}
	public void clickAppLaunchButton() {
		try {
			appLaunchButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(30));
			appLaunchButton.click();
			logStep("Clicked app launch button");
		} catch (Exception e) {
			throw new ElementException("Failed to click app launch button", e);
		}
	}
}
