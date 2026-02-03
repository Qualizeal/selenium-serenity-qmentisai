package com.serenity.pages;

import com.serenity.base.BasePage;
import com.serenity.exceptions.ElementException;
import com.serenity.exceptions.PageException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import java.time.Duration;

/**
 * Page Object for TAP Login and TAP Profile User search flows.
 * Encapsulates login and TAP profile search actions for TAP-related test scenarios.
 */
public class TAPLoginPage extends BasePage {

    // Locators for Salesforce login
    @FindBy(id = "username")
    private WebElementFacade usernameInput;

    @FindBy(id = "password")
    private WebElementFacade passwordInput;

    @FindBy(id = "Login")
    private WebElementFacade loginButton;

    // Locators for Setup and TAP Profile User search
    @FindBy(css = "a[aria-label='Setup']")
    private WebElementFacade setupButton;

    @FindBy(css = "input[placeholder='Search Setup']")
    private WebElementFacade setupSearchInput;

    // Locators for TAP Profile User page
    @FindBy(xpath = "//button[contains(text(),'Login')]" )
    private WebElementFacade tapUserLoginButton;

    // Locators for Home dropdown and Accounts navigation
    @FindBy(xpath = "//a[@title='Home']")
    private WebElementFacade homeNav;

    @FindBy(xpath = "//span[text()='Accounts']/ancestor::a")
    private WebElementFacade accountsOption;

    // Locators for Account selection
    @FindBy(xpath = "//span[text()='Client']/ancestor::tr//a")
    private WebElementFacade clientAccountLink;

    // Locators for Related tab
    @FindBy(xpath = "//a[@data-label='Related']")
    private WebElementFacade relatedTab;

    // Locators for Open Opportunities New button
    @FindBy(xpath = "//a[contains(text(),'Open Opportunities')]/ancestor::header/following-sibling::div//button[text()='New']")
    private WebElementFacade newOpportunityButton;

    // Locators for TAP Deal Intake radio and Next
    @FindBy(xpath = "//input[@type='radio' and following-sibling::label[contains(.,'TAP Deal Intake')]]")
    private WebElementFacade tapDealIntakeRadio;

    @FindBy(xpath = "//button[text()='Next']")
    private WebElementFacade nextButton;

    // Locators for Deal Type field
    @FindBy(xpath = "//label[contains(text(),'Deal Type')]/following-sibling::div//button")
    private WebElementFacade dealTypeDropdown;

    @FindBy(xpath = "//lightning-base-combobox-item//span[@title='P&C']")
    private WebElementFacade dealTypePCOption;

    // Locators for Primary Producer
    @FindBy(xpath = "//label[contains(text(),'Primary Producer')]/following-sibling::div//input")
    private WebElementFacade primaryProducerInput;

    // Locators for Opportunity Name
    @FindBy(xpath = "//label[contains(text(),'Opportunity Name')]/following-sibling::div//input")
    private WebElementFacade opportunityNameInput;

    // Locators for Deal Structure
    @FindBy(xpath = "//label[contains(text(),'Deal Structure')]/following-sibling::div//button")
    private WebElementFacade dealStructureDropdown;

    // Locators for Save button
    @FindBy(xpath = "//button[@name='SaveEdit' or text()='Save']")
    private WebElementFacade saveButton;

    // Locators for Success message
    @FindBy(xpath = "//span[contains(@class,'toastMessage') or contains(text(),'was created.')]" )
    private WebElementFacade successMessage;

    /**
     * Log in to Salesforce as a TAP user.
     * @param username Salesforce username
     * @param password Salesforce password
     */
    public void loginAsTapUser(String username, String password) {
        try {
            logStep("Entering Salesforce username");
            usernameInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            usernameInput.type(username);

            logStep("Entering Salesforce password");
            passwordInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            passwordInput.type(password);

            logStep("Clicking Login button");
            loginButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            loginButton.click();
        } catch (Exception e) {
            throw new ElementException("Failed to login as TAP user", e);
        }
    }

    /**
     * Navigate to TAP Profile User search via Setup and login as the specified TAP profile user.
     * @param tapProfileUserName Name of the TAP Profile User to search (e.g., Dillon Cox)
     */
    public void navigateToTapProfileSearch(String tapProfileUserName) {
        try {
            logStep("Clicking Setup button");
            setupButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            setupButton.click();

            logStep("Searching for TAP Profile User: " + tapProfileUserName);
            setupSearchInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            setupSearchInput.typeAndEnter(tapProfileUserName);

            // Wait for and click on the user link (dynamic)
            By userLinkBy = By.xpath("//a[contains(@title,'" + tapProfileUserName + "')]");
            WebElementFacade userLink = find(userLinkBy);
            userLink.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            userLink.click();

            logStep("Clicking Login button for TAP user");
            tapUserLoginButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            tapUserLoginButton.click();
        } catch (Exception e) {
            throw new PageException("Failed to navigate and login as TAP Profile User: " + tapProfileUserName, e);
        }
    }

    /**
     * Navigate to Accounts via Home dropdown.
     */
    public void navigateToAccounts() {
        try {
            logStep("Clicking Home navigation");
            homeNav.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            homeNav.click();

            logStep("Selecting Accounts option");
            accountsOption.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            accountsOption.click();
        } catch (Exception e) {
            throw new PageException("Failed to navigate to Accounts", e);
        }
    }

    /**
     * Select an account with Account Record Type = Client.
     * @param accountName Name of the account (e.g., 1031 Exchange Corporation)
     */
    public void selectClientAccount(String accountName) {
        try {
            logStep("Selecting Client account: " + accountName);
            By accountLinkBy = By.xpath("//a[contains(text(),'" + accountName + "')]");
            WebElementFacade accountLink = find(accountLinkBy);
            accountLink.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            accountLink.click();
        } catch (Exception e) {
            throw new ElementException("Failed to select client account: " + accountName, e);
        }
    }

    /**
     * Click the Related tab on the Account page.
     */
    public void clickRelatedTab() {
        try {
            logStep("Clicking Related tab");
            relatedTab.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            relatedTab.click();
        } catch (Exception e) {
            throw new ElementException("Failed to click Related tab", e);
        }
    }

    /**
     * Click the New button beside the Open Opportunities related list.
     */
    public void clickNewOpportunity() {
        try {
            logStep("Clicking New button beside Open Opportunities");
            newOpportunityButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            newOpportunityButton.click();
        } catch (Exception e) {
            throw new ElementException("Failed to click New Opportunity button", e);
        }
    }

    /**
     * Select TAP Deal Intake and click Next.
     */
    public void selectTapDealIntakeAndNext() {
        try {
            logStep("Selecting TAP Deal Intake radio button");
            tapDealIntakeRadio.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            tapDealIntakeRadio.click();

            logStep("Clicking Next button");
            nextButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            nextButton.click();
        } catch (Exception e) {
            throw new ElementException("Failed to select TAP Deal Intake and click Next", e);
        }
    }

    /**
     * Select P&C from the Deal Type field.
     */
    public void selectDealTypePC() {
        try {
            logStep("Opening Deal Type dropdown");
            dealTypeDropdown.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            dealTypeDropdown.click();

            logStep("Selecting P&C option");
            dealTypePCOption.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            dealTypePCOption.click();
        } catch (Exception e) {
            throw new ElementException("Failed to select Deal Type P&C", e);
        }
    }

    /**
     * Enter Primary Producer.
     * @param producerName Name of the producer to search/select
     */
    public void enterPrimaryProducer(String producerName) {
        try {
            logStep("Entering Primary Producer: " + producerName);
            primaryProducerInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            primaryProducerInput.typeAndEnter(producerName);
        } catch (Exception e) {
            throw new ElementException("Failed to enter Primary Producer", e);
        }
    }

    /**
     * Enter Opportunity Name.
     * @param opportunityName Name for the new opportunity
     */
    public void enterOpportunityName(String opportunityName) {
        try {
            logStep("Entering Opportunity Name: " + opportunityName);
            opportunityNameInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            opportunityNameInput.type(opportunityName);
        } catch (Exception e) {
            throw new ElementException("Failed to enter Opportunity Name", e);
        }
    }

    /**
     * Select Deal Structure from dropdown.
     * @param structureOption The option to select
     */
    public void selectDealStructure(String structureOption) {
        try {
            logStep("Opening Deal Structure dropdown");
            dealStructureDropdown.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            dealStructureDropdown.click();

            By structureOptionBy = By.xpath("//lightning-base-combobox-item//span[@title='" + structureOption + "']");
            WebElementFacade option = find(structureOptionBy);
            option.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            option.click();
        } catch (Exception e) {
            throw new ElementException("Failed to select Deal Structure: " + structureOption, e);
        }
    }

    /**
     * Click Save and verify success message.
     * @return true if success message is displayed
     */
    public boolean saveAndVerifyOpportunityCreated() {
        try {
            logStep("Clicking Save button");
            saveButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            saveButton.click();

            logStep("Waiting for success message");
            successMessage.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(30));
            return successMessage.isVisible();
        } catch (Exception e) {
            throw new ElementException("Failed to save Opportunity or verify success message", e);
        }
    }

    // Additional methods for other fields and steps can be added following the same pattern.
}
