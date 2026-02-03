package com.serenity.pages;

import com.serenity.base.BasePage;
import com.serenity.exceptions.ElementException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import java.time.Duration;
import java.util.logging.Logger;

/**
 * Page Object for TAP Opportunities - handles Open Opportunities related list and creation workflow in TAP.
 * Supports: Click New, select TAP Deal Intake, populate basic fields for Opportunity creation.
 */
public class TAPOpportunitiesPage extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(TAPOpportunitiesPage.class.getName());
    private static final int TIMEOUT = 30;

    // Locators for Open Opportunities related list
    @FindBy(xpath = "//a[text()='Open Opportunities (3)']")
    private WebElementFacade openOpportunitiesLink;

    @FindBy(xpath = "//button[text()='New']")
    private WebElementFacade newOpportunityButton;

    // New Opportunity: TAP Deal Intake radio and Next button
    @FindBy(xpath = "//input[@type='radio' and following-sibling::label//span[text()='TAP Deal Intake']]")
    private WebElementFacade tapDealIntakeRadio;

    @FindBy(xpath = "//button[text()='Next']")
    private WebElementFacade nextButton;

    // Basic fields on New Opportunity: TAP Deal Intake
    @FindBy(xpath = "//button[@aria-label='Deal Type']")
    private WebElementFacade dealTypeDropdown;

    @FindBy(xpath = "//span[contains(@class,'slds-truncate') and text()='P&C']")
    private WebElementFacade dealTypePCOption;

    @FindBy(xpath = "//input[@name='Name' or @id='input-933']")
    private WebElementFacade opportunityNameInput;

    @FindBy(xpath = "//button[@aria-label='Deal Structure']")
    private WebElementFacade dealStructureDropdown;

    @FindBy(xpath = "//input[@aria-label='Primary Producer']")
    private WebElementFacade primaryProducerLookupInput;

    @FindBy(xpath = "//button[@name='SaveEdit' or text()='Save']")
    private WebElementFacade saveButton;

    // --- Actions ---

    /**
     * Clicks the Related tab and then the New button beside Open Opportunities list.
     */
    public void clickNewOpportunityInOpenOpportunities() {
        try {
            openOpportunitiesLink.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            openOpportunitiesLink.click();
            logStep("Clicked Open Opportunities related list link");
            newOpportunityButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            newOpportunityButton.click();
            logStep("Clicked New button beside Open Opportunities");
        } catch (Exception e) {
            throw new ElementException("Failed to click New Opportunity in Open Opportunities list", e);
        }
    }

    /**
     * Selects the TAP Deal Intake radio and clicks Next.
     */
    public void selectTAPDealIntakeAndProceed() {
        try {
            tapDealIntakeRadio.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            tapDealIntakeRadio.click();
            logStep("Selected TAP Deal Intake radio button");
            nextButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            nextButton.click();
            logStep("Clicked Next button after selecting TAP Deal Intake");
        } catch (Exception e) {
            throw new ElementException("Failed to select TAP Deal Intake and proceed", e);
        }
    }

    /**
     * Selects Deal Type as P&C from the dropdown.
     */
    public void selectDealTypePandC() {
        try {
            dealTypeDropdown.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            dealTypeDropdown.click();
            logStep("Opened Deal Type dropdown");
            dealTypePCOption.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            dealTypePCOption.click();
            logStep("Selected Deal Type: P&C");
        } catch (Exception e) {
            throw new ElementException("Failed to select Deal Type P&C", e);
        }
    }

    /**
     * Enters Opportunity Name.
     * @param name Opportunity Name
     */
    public void enterOpportunityName(String name) {
        try {
            opportunityNameInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            opportunityNameInput.clear();
            opportunityNameInput.type(name);
            logStep("Entered Opportunity Name: " + name);
        } catch (Exception e) {
            throw new ElementException("Failed to enter Opportunity Name", e);
        }
    }

    /**
     * Selects a Deal Structure value from the dropdown.
     * @param structureValue The visible text of the Deal Structure option
     */
    public void selectDealStructure(String structureValue) {
        try {
            dealStructureDropdown.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            dealStructureDropdown.click();
            WebElementFacade structureOption = find(By.xpath("//span[contains(@class,'slds-truncate') and text()='" + structureValue + "']"));
            structureOption.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            structureOption.click();
            logStep("Selected Deal Structure: " + structureValue);
        } catch (Exception e) {
            throw new ElementException("Failed to select Deal Structure: " + structureValue, e);
        }
    }

    /**
     * Searches and selects a Primary Producer from the lookup field.
     * @param producerName The name of the producer to search and select
     */
    public void selectPrimaryProducer(String producerName) {
        try {
            primaryProducerLookupInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            primaryProducerLookupInput.clear();
            primaryProducerLookupInput.type(producerName);
            logStep("Typed Primary Producer: " + producerName);
            // Wait for dropdown and select
            WebElementFacade dropdownOption = find(By.xpath("//span[@class='slds-listbox__option-text slds-listbox__option-text_entity' and contains(text(), '" + producerName + "')]"));
            dropdownOption.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            dropdownOption.click();
            logStep("Selected Primary Producer from dropdown: " + producerName);
        } catch (Exception e) {
            throw new ElementException("Failed to select Primary Producer: " + producerName, e);
        }
    }

    /**
     * Clicks the Save button to create the Opportunity.
     */
    public void clickSaveButton() {
        try {
            saveButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(TIMEOUT));
            saveButton.click();
            logStep("Clicked Save button to create Opportunity");
        } catch (Exception e) {
            throw new ElementException("Failed to click Save button", e);
        }
    }

    /**
     * Composite workflow: Create new TAP Deal Intake Opportunity with Deal Type P&C and minimal fields.
     * @param opportunityName Opportunity Name
     * @param dealStructure Deal Structure value
     * @param primaryProducer Primary Producer name
     */
    public void createNewTAPDealIntakeOpportunity(String opportunityName, String dealStructure, String primaryProducer) {
        clickNewOpportunityInOpenOpportunities();
        selectTAPDealIntakeAndProceed();
        selectDealTypePandC();
        enterOpportunityName(opportunityName);
        selectDealStructure(dealStructure);
        selectPrimaryProducer(primaryProducer);
        clickSaveButton();
    }

    // Add further field population methods as needed for full workflow coverage
}
