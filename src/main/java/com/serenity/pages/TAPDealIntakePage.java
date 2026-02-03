package com.serenity.pages;

import com.serenity.base.BasePage;
import com.serenity.exceptions.ElementException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import java.time.Duration;
import java.util.List;

/**
 * Page Object for the New TAP Deal Intake Opportunity creation page in Salesforce.
 * Provides methods to fill required fields, select options, and handle dual-list boxes.
 * Follows repository patterns and best practices for maintainability and reliability.
 */
public class TAPDealIntakePage extends BasePage {

    // --- Locators for required fields ---
    @FindBy(id = "combobox-button-924")
    private WebElementFacade dealTypeCombobox;

    @FindBy(id = "combobox-input-1233")
    private WebElementFacade primaryProducerInput;

    @FindBy(id = "input-933")
    private WebElementFacade opportunityNameInput;

    @FindBy(id = "combobox-button-945")
    private WebElementFacade dealStructureCombobox;

    @FindBy(xpath = "//div[@aria-labelledby='group-label-955']")
    private WebElementFacade pncStrategyDualListbox;

    @FindBy(xpath = "//div[@aria-labelledby='group-label-970']")
    private WebElementFacade peopleSolutionsStrategyDualListbox;

    @FindBy(id = "input-983")
    private WebElementFacade signDateInput;

    @FindBy(id = "combobox-input-1244")
    private WebElementFacade buyerLookupInput;

    @FindBy(id = "combobox-input-1252")
    private WebElementFacade targetLookupInput;

    @FindBy(id = "input-992")
    private WebElementFacade targetCloseDateInput;

    @FindBy(id = "combobox-button-1015")
    private WebElementFacade repsWarrantyCombobox;

    @FindBy(id = "input-989")
    private WebElementFacade numberOfEmployeesInput;

    @FindBy(id = "input-1031")
    private WebElementFacade revenueInput;

    @FindBy(id = "combobox-button-1007")
    private WebElementFacade diligenceFeeAgreementCombobox;

    @FindBy(id = "combobox-button-1041")
    private WebElementFacade loiSignedCombobox;

    @FindBy(id = "combobox-button-1025")
    private WebElementFacade stageCombobox;

    @FindBy(id = "input-1046")
    private WebElementFacade deliverableDueDateInput;

    @FindBy(xpath = "//button[@name='SaveEdit']")
    private WebElementFacade saveButton;

    // --- Dual-list box buttons ---
    private static final By MOVE_TO_CHOSEN_BUTTON = By.xpath("//button[@title='Move selection to Chosen']");
    private static final By MOVE_TO_AVAILABLE_BUTTON = By.xpath("//button[@title='Move selection to Available']");

    // --- Utility methods for waits and actions ---
    private static final int FIELD_TIMEOUT = 30;

    /**
     * Selects a value from a combobox by visible text.
     */
    private void selectFromCombobox(WebElementFacade combobox, String optionText) {
        try {
            combobox.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(FIELD_TIMEOUT));
            combobox.click();
            String optionXpath = String.format("//lightning-base-combobox-item//span[text()='%s']", optionText);
            WebElementFacade option = find(By.xpath(optionXpath));
            option.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(FIELD_TIMEOUT));
            option.click();
        } catch (Exception e) {
            throw new ElementException("Failed to select '" + optionText + "' from combobox", e);
        }
    }

    /**
     * Types text into a field with wait.
     */
    private void typeIntoField(WebElementFacade input, String value) {
        try {
            input.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(FIELD_TIMEOUT));
            input.clear();
            input.type(value);
        } catch (Exception e) {
            throw new ElementException("Failed to type into field", e);
        }
    }

    /**
     * Selects a date in a date input field.
     */
    private void setDateField(WebElementFacade dateInput, String dateValue) {
        typeIntoField(dateInput, dateValue);
    }

    /**
     * Selects an item from a lookup field by typing and picking the first suggestion.
     */
    private void selectFromLookup(WebElementFacade lookupInput, String searchText) {
        try {
            lookupInput.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(FIELD_TIMEOUT));
            lookupInput.clear();
            lookupInput.typeAndEnter(searchText);
            // Wait for dropdown and select first result
            String resultXpath = "//ul[contains(@class,'slds-listbox')]//li[1]//span[@class='slds-media__body']/span";
            WebElementFacade firstResult = find(By.xpath(resultXpath));
            firstResult.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(FIELD_TIMEOUT));
            firstResult.click();
        } catch (Exception e) {
            throw new ElementException("Failed to select from lookup for: " + searchText, e);
        }
    }

    /**
     * Moves an item from Available to Chosen in a dual-list box.
     */
    private void moveItemToChosen(WebElementFacade dualListbox, String itemText) {
        try {
            String availableOptionXpath = String.format(".//div[contains(@class,'slds-dueling-list__column_responsive')]//div[@id and contains(@id,'source-list')]//div[@data-value='%s']", itemText);
            WebElementFacade option = dualListbox.then(By.xpath(availableOptionXpath));
            option.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(FIELD_TIMEOUT));
            option.click();
            WebElementFacade moveButton = dualListbox.then(MOVE_TO_CHOSEN_BUTTON);
            moveButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(FIELD_TIMEOUT));
            moveButton.click();
        } catch (Exception e) {
            throw new ElementException("Failed to move item '" + itemText + "' to Chosen in dual-list box", e);
        }
    }

    // --- Public methods for test steps ---

    public void selectDealType(String dealType) {
        selectFromCombobox(dealTypeCombobox, dealType);
    }

    public void selectPrimaryProducer(String producerName) {
        selectFromLookup(primaryProducerInput, producerName);
    }

    public void enterOpportunityName(String opportunityName) {
        typeIntoField(opportunityNameInput, opportunityName);
    }

    public void selectDealStructure(String dealStructure) {
        selectFromCombobox(dealStructureCombobox, dealStructure);
    }

    public void selectPncStrategy(String strategy) {
        moveItemToChosen(pncStrategyDualListbox, strategy);
    }

    public void selectPeopleSolutionsStrategy(String strategy) {
        moveItemToChosen(peopleSolutionsStrategyDualListbox, strategy);
    }

    public void setSignDate(String signDate) {
        setDateField(signDateInput, signDate);
    }

    public void selectBuyer(String buyerAccount) {
        selectFromLookup(buyerLookupInput, buyerAccount);
    }

    public void selectTarget(String targetAccount) {
        selectFromLookup(targetLookupInput, targetAccount);
    }

    public void setTargetCloseDate(String closeDate) {
        setDateField(targetCloseDateInput, closeDate);
    }

    public void selectRepsWarranty(String value) {
        selectFromCombobox(repsWarrantyCombobox, value);
    }

    public void enterNumberOfEmployees(String numEmployees) {
        typeIntoField(numberOfEmployeesInput, numEmployees);
    }

    public void enterRevenue(String revenue) {
        typeIntoField(revenueInput, revenue);
    }

    public void selectDiligenceFeeAgreement(String value) {
        selectFromCombobox(diligenceFeeAgreementCombobox, value);
    }

    public void selectLoiSigned(String value) {
        selectFromCombobox(loiSignedCombobox, value);
    }

    public void selectStage(String stage) {
        selectFromCombobox(stageCombobox, stage);
    }

    public void setDeliverableDueDate(String dueDate) {
        setDateField(deliverableDueDateInput, dueDate);
    }

    public void clickSave() {
        try {
            saveButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(FIELD_TIMEOUT));
            saveButton.click();
        } catch (Exception e) {
            throw new ElementException("Failed to click Save button", e);
        }
    }

    /**
     * Fills all required fields for a new TAP Deal Intake Opportunity (P&C type).
     * This is a composite action for convenience.
     */
    public void fillRequiredFields(
            String dealType,
            String primaryProducer,
            String opportunityName,
            String dealStructure,
            String pncStrategy,
            String peopleSolutionsStrategy,
            String signDate,
            String buyerAccount,
            String targetAccount,
            String targetCloseDate,
            String repsWarranty,
            String numEmployees,
            String revenue,
            String diligenceFeeAgreement,
            String loiSigned,
            String stage,
            String deliverableDueDate
    ) {
        selectDealType(dealType);
        selectPrimaryProducer(primaryProducer);
        enterOpportunityName(opportunityName);
        selectDealStructure(dealStructure);
        if (pncStrategy != null && !pncStrategy.isEmpty()) {
            selectPncStrategy(pncStrategy);
        }
        if (peopleSolutionsStrategy != null && !peopleSolutionsStrategy.isEmpty()) {
            selectPeopleSolutionsStrategy(peopleSolutionsStrategy);
        }
        setSignDate(signDate);
        selectBuyer(buyerAccount);
        selectTarget(targetAccount);
        setTargetCloseDate(targetCloseDate);
        selectRepsWarranty(repsWarranty);
        enterNumberOfEmployees(numEmployees);
        enterRevenue(revenue);
        selectDiligenceFeeAgreement(diligenceFeeAgreement);
        selectLoiSigned(loiSigned);
        selectStage(stage);
        setDeliverableDueDate(deliverableDueDate);
    }
}
