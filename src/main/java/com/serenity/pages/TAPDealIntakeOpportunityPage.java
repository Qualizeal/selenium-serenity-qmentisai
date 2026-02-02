package com.serenity.pages;

import com.serenity.base.BasePage;
import com.serenity.reporting.ReportManager;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

/**
 * Page Object for TAP Deal Intake Opportunity creation flow (P&C).
 * Encapsulates all high-level actions and verifications for TC_001_New TAP Deal Intake Opportunity (P&C).
 */
public class TAPDealIntakeOpportunityPage extends BasePage {

    // --- Locators (placeholders, update as needed) ---
    @FindBy(xpath = "//a[text()='Open Opportunities (0)']")
    private WebElementFacade openOpportunitiesLink;

    @FindBy(xpath = "//button[text()='New']")
    private WebElementFacade newOpportunityButton;

    @FindBy(xpath = "//input[@placeholder='Search Opportunity']")
    private WebElementFacade searchOpportunityInput;

    @FindBy(xpath = "//input[@id='radio-0-332']")
    private WebElementFacade tapDealIntakeRadio;

    @FindBy(xpath = "//button[text()='Next']")
    private WebElementFacade nextButton;

    @FindBy(id = "combobox-button-361")
    private WebElementFacade dealTypeCombobox;

    @FindBy(id = "combobox-input-671")
    private WebElementFacade primaryProducerInput;

    @FindBy(id = "input-370")
    private WebElementFacade opportunityNameInput;

    @FindBy(id = "combobox-button-383")
    private WebElementFacade dealStructureCombobox;

    @FindBy(id = "group-label-393")
    private WebElementFacade pcStrategyDualListLabel;

    @FindBy(xpath = "//div[@id='group-label-393']/following-sibling::div//button[@title='Move selection to Chosen']")
    private WebElementFacade pcStrategyMoveToChosenButton;

    @FindBy(id = "source-list-393")
    private WebElementFacade pcStrategyAvailableList;

    @FindBy(id = "group-label-408")
    private WebElementFacade peopleSolutionsStrategyDualListLabel;

    @FindBy(xpath = "//div[@id='group-label-408']/following-sibling::div//button[@title='Move selection to Chosen']")
    private WebElementFacade peopleSolutionsStrategyMoveToChosenButton;

    @FindBy(id = "source-list-408")
    private WebElementFacade peopleSolutionsStrategyAvailableList;

    @FindBy(id = "input-421")
    private WebElementFacade signDateInput;

    @FindBy(id = "combobox-input-682")
    private WebElementFacade buyerInput;

    @FindBy(id = "combobox-input-690")
    private WebElementFacade targetInput;

    @FindBy(id = "input-430")
    private WebElementFacade targetCloseDateInput;

    @FindBy(id = "combobox-button-453")
    private WebElementFacade repsAndWarrantyCombobox;

    @FindBy(id = "input-427")
    private WebElementFacade numberOfEmployeesInput;

    @FindBy(id = "input-469")
    private WebElementFacade revenueInput;

    @FindBy(id = "combobox-button-445")
    private WebElementFacade diligenceFeeAgreementCombobox;

    @FindBy(id = "combobox-button-479")
    private WebElementFacade loiSignedCombobox;

    @FindBy(id = "combobox-button-463")
    private WebElementFacade stageCombobox;

    @FindBy(id = "input-484")
    private WebElementFacade deliverableDueDateInput;

    @FindBy(xpath = "//button[@name='SaveEdit']")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//span[contains(text(),'was created')]" )
    private WebElementFacade opportunityCreatedMessage;

    // --- Navigation and High-Level Actions ---

    /**
     * Navigates to the specified account and opens the Opportunities related list.
     * @param accountName The name of the account to search and select.
     */
    public void navigateToAccountAndOpenOpportunities(String accountName) {
        logStep("Navigating to account: " + accountName + " and opening Opportunities list");
        // Assumes already on Accounts page
        searchOpportunityInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        searchOpportunityInput.type(accountName);
        searchOpportunityInput.sendKeys(Keys.ENTER);
        waitForPageLoad();
        WebElementFacade accountLink = find(By.xpath("//a[contains(text(),'" + accountName + "')]"));
        waitForElementVisible(accountLink, DEFAULT_TIMEOUT);
        accountLink.click();
        waitForPageLoad();
        WebElementFacade relatedTab = find(By.xpath("//a[@data-label='Related']"));
        waitForElementVisible(relatedTab, DEFAULT_TIMEOUT);
        relatedTab.click();
        waitForPageLoad();
        waitForElementVisible(openOpportunitiesLink, DEFAULT_TIMEOUT);
        openOpportunitiesLink.click();
        waitForPageLoad();
    }

    /**
     * Clicks the 'New' button to start a new Opportunity.
     */
    public void clickNewOpportunity() {
        logStep("Clicking New Opportunity button");
        waitForElementClickable(newOpportunityButton, DEFAULT_TIMEOUT);
        newOpportunityButton.click();
        waitForPageLoad();
    }

    /**
     * Selects the TAP Deal Intake radio and clicks Next.
     */
    public void selectTapDealIntakeRadioAndNext() {
        logStep("Selecting TAP Deal Intake radio and clicking Next");
        tapDealIntakeRadio.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        tapDealIntakeRadio.click();
        nextButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        nextButton.click();
        waitForPageLoad();
    }

    /**
     * Sets Deal Type to P&C.
     */
    public void setDealTypePC() {
        logStep("Setting Deal Type to P&C");
        dealTypeCombobox.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        dealTypeCombobox.click();
        WebElementFacade pcOption = find(By.xpath("//span[@class='slds-truncate' and text()='P&C']"));
        waitForElementVisible(pcOption, DEFAULT_TIMEOUT);
        pcOption.click();
    }

    /**
     * Searches and selects the Primary Producer.
     * @param producerName The producer to search and select.
     */
    public void searchAndSelectPrimaryProducer(String producerName) {
        logStep("Searching and selecting Primary Producer: " + producerName);
        primaryProducerInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        primaryProducerInput.clear();
        primaryProducerInput.type(producerName);
        waitFor(Duration.ofSeconds(2));
        WebElementFacade producerOption = find(By.xpath("//span[@class='slds-media__body']//span[contains(text(),'" + producerName + "')]"));
        waitForElementVisible(producerOption, DEFAULT_TIMEOUT);
        producerOption.click();
    }

    /**
     * Enters the Opportunity Name.
     * @param name Opportunity name
     */
    public void enterOpportunityName(String name) {
        logStep("Entering Opportunity Name: " + name);
        opportunityNameInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        opportunityNameInput.clear();
        opportunityNameInput.type(name);
    }

    /**
     * Selects the Deal Structure.
     * @param structure Structure name
     */
    public void selectDealStructure(String structure) {
        logStep("Selecting Deal Structure: " + structure);
        dealStructureCombobox.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        dealStructureCombobox.click();
        WebElementFacade structureOption = find(By.xpath("//span[@class='slds-truncate' and text()='" + structure + "']"));
        waitForElementVisible(structureOption, DEFAULT_TIMEOUT);
        structureOption.click();
    }

    /**
     * Adds P&C Strategy items from Available to Chosen.
     * @param items Array of item names to add
     */
    public void addPcStrategy(String[] items) {
        logStep("Adding P&C Strategy items: " + String.join(", ", items));
        for (String item : items) {
            WebElementFacade availableItem = pcStrategyAvailableList.then(By.xpath(".//span[@title='" + item + "']"));
            waitForElementVisible(availableItem, DEFAULT_TIMEOUT);
            availableItem.click();
        }
        pcStrategyMoveToChosenButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        pcStrategyMoveToChosenButton.click();
    }

    /**
     * Adds People Solutions Strategy items from Available to Chosen.
     * @param items Array of item names to add
     */
    public void addPeopleSolutionsStrategy(String[] items) {
        logStep("Adding People Solutions Strategy items: " + String.join(", ", items));
        for (String item : items) {
            WebElementFacade availableItem = peopleSolutionsStrategyAvailableList.then(By.xpath(".//span[@title='" + item + "']"));
            waitForElementVisible(availableItem, DEFAULT_TIMEOUT);
            availableItem.click();
        }
        peopleSolutionsStrategyMoveToChosenButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        peopleSolutionsStrategyMoveToChosenButton.click();
    }

    /**
     * Sets the Sign Date.
     * @param date Date string (MM/dd/yyyy or project format)
     */
    public void setSignDate(String date) {
        logStep("Setting Sign Date: " + date);
        signDateInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        signDateInput.clear();
        signDateInput.type(date);
        signDateInput.sendKeys(Keys.TAB);
    }

    /**
     * Selects the Buyer account.
     * @param buyerAccount Buyer account name
     */
    public void selectBuyer(String buyerAccount) {
        logStep("Selecting Buyer: " + buyerAccount);
        buyerInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        buyerInput.clear();
        buyerInput.type(buyerAccount);
        waitFor(Duration.ofSeconds(2));
        WebElementFacade buyerOption = find(By.xpath("//span[@class='slds-media__body']//span[contains(text(),'" + buyerAccount + "')]"));
        waitForElementVisible(buyerOption, DEFAULT_TIMEOUT);
        buyerOption.click();
    }

    /**
     * Selects the Target account.
     * @param targetAccount Target account name
     */
    public void selectTarget(String targetAccount) {
        logStep("Selecting Target: " + targetAccount);
        targetInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        targetInput.clear();
        targetInput.type(targetAccount);
        waitFor(Duration.ofSeconds(2));
        WebElementFacade targetOption = find(By.xpath("//span[@class='slds-media__body']//span[contains(text(),'" + targetAccount + "')]"));
        waitForElementVisible(targetOption, DEFAULT_TIMEOUT);
        targetOption.click();
    }

    /**
     * Sets the Target Close Date.
     * @param date Date string
     */
    public void setTargetCloseDate(String date) {
        logStep("Setting Target Close Date: " + date);
        targetCloseDateInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        targetCloseDateInput.clear();
        targetCloseDateInput.type(date);
        targetCloseDateInput.sendKeys(Keys.TAB);
    }

    /**
     * Selects Reps & Warranty option.
     * @param option Option value
     */
    public void setRepsAndWarranty(String option) {
        logStep("Selecting Reps & Warranty: " + option);
        repsAndWarrantyCombobox.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        repsAndWarrantyCombobox.click();
        WebElementFacade optionElement = find(By.xpath("//span[@class='slds-truncate' and text()='" + option + "']"));
        waitForElementVisible(optionElement, DEFAULT_TIMEOUT);
        optionElement.click();
    }

    /**
     * Enters numeric fields: Number of Employees and Revenue.
     * @param numberOfEmployees Number of employees
     * @param revenue Revenue value
     */
    public void enterNumericFields(String numberOfEmployees, String revenue) {
        logStep("Entering Number of Employees: " + numberOfEmployees + ", Revenue: " + revenue);
        numberOfEmployeesInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        numberOfEmployeesInput.clear();
        numberOfEmployeesInput.type(numberOfEmployees);
        revenueInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        revenueInput.clear();
        revenueInput.type(revenue);
    }

    /**
     * Sets Diligence Fee Agreement.
     * @param option Option value
     */
    public void setDiligenceFeeAgreement(String option) {
        logStep("Selecting Diligence Fee Agreement: " + option);
        diligenceFeeAgreementCombobox.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        diligenceFeeAgreementCombobox.click();
        WebElementFacade optionElement = find(By.xpath("//span[@class='slds-truncate' and text()='" + option + "']"));
        waitForElementVisible(optionElement, DEFAULT_TIMEOUT);
        optionElement.click();
    }

    /**
     * Sets LOI Signed option.
     * @param option Option value
     */
    public void setLoiSigned(String option) {
        logStep("Selecting LOI Signed: " + option);
        loiSignedCombobox.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        loiSignedCombobox.click();
        WebElementFacade optionElement = find(By.xpath("//span[@class='slds-truncate' and text()='" + option + "']"));
        waitForElementVisible(optionElement, DEFAULT_TIMEOUT);
        optionElement.click();
    }

    /**
     * Selects the Stage.
     * @param stage Stage name
     */
    public void selectStage(String stage) {
        logStep("Selecting Stage: " + stage);
        stageCombobox.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        stageCombobox.click();
        WebElementFacade stageOption = find(By.xpath("//span[@class='slds-truncate' and text()='" + stage + "']"));
        waitForElementVisible(stageOption, DEFAULT_TIMEOUT);
        stageOption.click();
    }

    /**
     * Sets Deliverable Due Date.
     * @param date Date string
     */
    public void setDeliverableDueDate(String date) {
        logStep("Setting Deliverable Due Date: " + date);
        deliverableDueDateInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
        deliverableDueDateInput.clear();
        deliverableDueDateInput.type(date);
        deliverableDueDateInput.sendKeys(Keys.TAB);
    }

    /**
     * Clicks Save to create the Opportunity.
     */
    public void saveOpportunity() {
        logStep("Saving Opportunity");
        waitForElementClickable(saveButton, DEFAULT_TIMEOUT);
        saveButton.click();
        waitForPageLoad();
    }

    /**
     * Verifies that the Opportunity was created successfully.
     * @param expectedName Expected Opportunity name
     */
    public void verifyOpportunityCreated(String expectedName) {
        logStep("Verifying Opportunity creation for: " + expectedName);
        boolean created = isOpportunityCreated(expectedName);
        if (!created) {
            captureFailureScreenshot("OpportunityCreationFailed");
            throw new AssertionError("Opportunity creation failed for: " + expectedName);
        }
        logStep("Opportunity created successfully: " + expectedName);
    }

    /**
     * Helper to check if Opportunity was created (success message or URL change).
     * @param expectedName Expected Opportunity name
     * @return true if created
     */
    public boolean isOpportunityCreated(String expectedName) {
        try {
            opportunityCreatedMessage.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(15));
            String msg = opportunityCreatedMessage.getText();
            if (msg != null && msg.contains(expectedName) && msg.contains("was created")) {
                logStep("Found success message: " + msg);
                return true;
            }
        } catch (Exception e) {
            // fallback: check if URL changed to Opportunity detail page
            String url = getPageUrl();
            if (url != null && url.contains("/Opportunity/")) {
                logStep("URL indicates Opportunity detail page: " + url);
                return true;
            }
        }
        return false;
    }

    /**
     * Captures a screenshot and logs failure in the report.
     * @param screenshotName Name for the screenshot
     */
    public void captureFailureScreenshot(String screenshotName) {
        logStep("Capturing screenshot for failure: " + screenshotName);
        ReportManager.takeScreenshot(screenshotName);
    }

    // --- Utility: Wait for seconds ---
    private void waitFor(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // --- Placeholder for chevron/stage validation methods (not implemented here) ---
    // public void validateChevronsAndStages() { ... }

    // --- Logging wrapper ---
    private void logStep(String stepDescription) {
        ReportManager.recordStep(stepDescription);
    }
}
