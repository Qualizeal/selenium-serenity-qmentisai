package com.serenity.pages;

import com.serenity.base.BasePage;
import com.serenity.exceptions.ElementException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import java.time.Duration;
import java.util.List;

/**
 * Page Object for Salesforce TAP Accounts page.
 * Supports navigation to Accounts list, filtering/selecting Account with Record Type 'Client',
 * and navigating to its details page.
 * Follows explicit wait and element interaction patterns as per framework best practices.
 */
public class TAPAccountsPage extends BasePage {

    // Locators for Accounts List Page
    @FindBy(xpath = "//span[text()='Recently Viewed']")
    private WebElementFacade recentlyViewedDropdown;

    @FindBy(xpath = "//input[@placeholder='Search this list...']")
    private WebElementFacade searchListInput;

    @FindBy(xpath = "//th[@aria-label='Account Record Type']")
    private WebElementFacade accountRecordTypeColumnHeader;

    @FindBy(xpath = "//th[@aria-label='Account Name']")
    private WebElementFacade accountNameColumnHeader;

    // Table rows: Each row in the Accounts table
    private static final By ACCOUNT_ROWS = By.xpath("//table//tbody/tr");

    // Within a row: Account Name link
    private static final String ACCOUNT_NAME_LINK_XPATH = ".//th//a";
    // Within a row: Account Record Type cell
    private static final String ACCOUNT_RECORD_TYPE_CELL_XPATH = ".//td[@data-label='Account Record Type']//a | .//td[@data-label='Account Record Type']//span";

    // Details page: Account Record Type field
    @FindBy(xpath = "//p[@title='Account Record Type']/following-sibling::p//span")
    private WebElementFacade accountRecordTypeDetailField;

    // Details page: Related tab
    @FindBy(xpath = "//a[@data-label='Related']")
    private WebElementFacade relatedTab;

    /**
     * Selects an account with Account Record Type = 'Client' from the Accounts list.
     * Navigates to its details page.
     *
     * @param accountName (Optional) If provided, selects the account by name and record type.
     *                    If null, selects the first account with Record Type 'Client'.
     * @throws ElementException if no matching account is found or navigation fails.
     */
    public void selectClientAccountAndNavigateToDetails(String accountName) {
        logStep("Selecting an Account with Record Type 'Client'" + (accountName != null ? (" and name '" + accountName + "'") : ""));
        waitForPageLoad();
        waitForElementVisible(accountRecordTypeColumnHeader, DEFAULT_TIMEOUT);
        List<WebElementFacade> rows = findAll(ACCOUNT_ROWS);
        boolean found = false;
        for (WebElementFacade row : rows) {
            WebElementFacade recordTypeCell = null;
            try {
                recordTypeCell = row.then(By.xpath(ACCOUNT_RECORD_TYPE_CELL_XPATH));
            } catch (Exception ignored) {}
            if (recordTypeCell == null || !recordTypeCell.isVisible()) continue;
            String recordType = recordTypeCell.getText().trim();
            if ("Client".equalsIgnoreCase(recordType)) {
                WebElementFacade nameLink = row.then(By.xpath(ACCOUNT_NAME_LINK_XPATH));
                if (accountName == null || nameLink.getText().trim().equalsIgnoreCase(accountName)) {
                    nameLink.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
                    nameLink.click();
                    waitForPageLoad();
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            throw new ElementException("No account with Record Type 'Client'" + (accountName != null ? (" and name '" + accountName + "'") : "") + " found in the list.");
        }
        // Wait for details page to load and verify Record Type
        waitForElementVisible(accountRecordTypeDetailField, DEFAULT_TIMEOUT);
        String detailRecordType = accountRecordTypeDetailField.getText().trim();
        if (!"Client".equalsIgnoreCase(detailRecordType)) {
            throw new ElementException("Navigated to account details, but Record Type is not 'Client'. Found: '" + detailRecordType + "'");
        }
        logStep("Navigated to Account details page for a 'Client' account.");
    }

    /**
     * Navigates to the Related tab on the Account details page.
     * Waits until the Related tab is clickable and visible.
     */
    public void goToRelatedTab() {
        logStep("Navigating to Related tab on Account details page.");
        waitForElementVisible(relatedTab, DEFAULT_TIMEOUT);
        relatedTab.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        relatedTab.click();
        waitForPageLoad();
    }

    /**
     * Searches for an account by name in the Accounts list.
     *
     * @param accountName Name of the account to search for.
     */
    public void searchAccountByName(String accountName) {
        logStep("Searching for account by name: '" + accountName + "'");
        waitForElementVisible(searchListInput, DEFAULT_TIMEOUT);
        searchListInput.clear();
        searchListInput.type(accountName);
        searchListInput.sendKeys("\n");
        waitForPageLoad();
    }
}
