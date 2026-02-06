package com.serenity.pages;

import com.serenity.base.BasePage;
import com.serenity.exceptions.ElementException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * InvestmentPolicyTemplatePage represents the Investment Policy Template page.
 * Contains all locators and actions related to editing and updating investment policy templates.
 * Extended to support creation of new policy templates.
 */
public class InvestmentPolicyTemplatePage extends BasePage {

    @FindBy(xpath = "//form[@id='template-update']")
    private WebElementFacade templateUpdateForm;

    @FindBy(xpath = "//iframe[@id='ui-tinymce-1_ifr']//body[@id='tinymce']")
    private WebElementFacade tinymceTextArea;

    @FindBy(xpath = "//button[text()='Update']")
    private WebElementFacade updateButton;

    @FindBy(xpath = "//button[text()='Make Update']")
    private WebElementFacade makeUpdateButton;

    // --- New Template Creation Locators ---

    // + ADD NEW POLICY TEMPLATE button
    @FindBy(id = "investmentPolicyTemplateList_buttonMenu_addNewPolicyTemplate")
    private WebElementFacade addNewPolicyTemplateButton;

    // Template Name input in the new template panel
    @FindBy(id = "policyTemplateName")
    private WebElementFacade templateNameInput;

    // Asset Allocation Type radio options (With Targets & Ranges, Peer Group Name Only, None)
    @FindBy(xpath = "//input[@name='assetAllocationType']")
    private List<WebElementFacade> assetAllocationTypeRadios;

    // New Policy Template side panel header
    @FindBy(xpath = "//h2[contains(text(),'New Investment Policy Template')]")
    private WebElementFacade newPolicyPanelHeader;

    /**
     * Set text to the TinyMCE editor text area
     * @param text - the text to enter into the editor
     * @throws ElementException if text area is not interactable
     */
    public void setTinymceText(String text) {
        try {
            tinymceTextArea.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(10));
            tinymceTextArea.clear();
            tinymceTextArea.type(text);
            logStep("Entered text into TinyMCE editor: " + text);
        } catch (Exception ex) {
            throw new ElementException("Failed to set text in TinyMCE editor", ex);
        }
    }

    /**
     * Click on the Update button
     * @throws ElementException if Update button is not clickable
     */
    public void clickUpdateButton() {
        try {
            updateButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(30));
            updateButton.click();
            logStep("Clicked Update button");
        } catch (Exception e) {
            throw new ElementException("Failed to click Update button", e);
        }
    }

    /**
     * Click on the Make Update button
     * @throws ElementException if Make Update button is not clickable
     */
    public void clickMakeUpdateButton() {
        try {
            makeUpdateButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(30));
            makeUpdateButton.click();
            logStep("Clicked Make Update button");
        } catch (Exception e) {
            throw new ElementException("Failed to click Make Update button", e);
        }
    }

    /**
     * Complete template update workflow: set text and click Update button
     * @param text - the text to enter into the editor
     * @throws ElementException if any step fails
     */
    public void updateTemplateWithText(String text) {
        setTinymceText(text);
        clickUpdateButton();
        clickMakeUpdateButton();
    }

    // --- New Template Creation Actions ---

    /**
     * Click the '+ ADD NEW POLICY TEMPLATE' button to open the new template panel.
     * @throws ElementException if button is not clickable
     */
    public void openNewPolicyTemplatePanel() {
        try {
            addNewPolicyTemplateButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(30));
            addNewPolicyTemplateButton.click();
            logStep("Clicked '+ ADD NEW POLICY TEMPLATE' button");
        } catch (Exception e) {
            throw new ElementException("Failed to open New Policy Template panel", e);
        }
    }

    /**
     * Checks if the New Investment Policy Template panel is displayed.
     * @return true if displayed, false otherwise
     */
    public boolean isNewPolicyPanelDisplayed() {
        try {
            return newPolicyPanelHeader.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the list of asset allocation option labels available in the new template panel.
     * @return List of option labels
     */
    public List<String> getAssetAllocationOptions() {
        List<String> options = new ArrayList<>();
        try {
            for (WebElementFacade radio : assetAllocationTypeRadios) {
                // Get the parent label text
                String labelText = radio.findByXpath("./ancestor::label").getText().trim();
                if (!labelText.isEmpty()) {
                    options.add(labelText.replaceAll("^\\d+", "").trim());
                }
            }
        } catch (Exception e) {
            // If radios not found, return empty list
        }
        return options;
    }

    /**
     * Selects an asset allocation type option by visible label.
     * @param option The label to select (e.g., "With Targets & Ranges")
     * @throws ElementException if option not found
     */
    public void selectAssetAllocationOption(String option) {
        boolean found = false;
        try {
            for (WebElementFacade radio : assetAllocationTypeRadios) {
                String labelText = radio.findByXpath("./ancestor::label").getText().trim();
                if (labelText.equalsIgnoreCase(option)) {
                    radio.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(10));
                    radio.click();
                    logStep("Selected asset allocation option: " + option);
                    found = true;
                    break;
                }
            }
        } catch (Exception e) {
            // ignore, handled below
        }
        if (!found) {
            throw new ElementException("Asset allocation option not found: " + option);
        }
    }

    /**
     * Sets the template name in the new template panel.
     * @param name The template name to enter
     * @throws ElementException if input is not interactable
     */
    public void setTemplateName(String name) {
        try {
            templateNameInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(10));
            templateNameInput.clear();
            templateNameInput.type(name);
            logStep("Entered template name: " + name);
        } catch (Exception e) {
            throw new ElementException("Failed to set template name", e);
        }
    }

    /**
     * Clicks the '+ ADD NEW POLICY TEMPLATE' button if it is visible and enabled.
     * Provided for completeness if conditional clicking is needed.
     * @throws ElementException if button is not clickable
     */
    public void clickAddNewPolicyTemplateIfApplicable() {
        try {
            if (addNewPolicyTemplateButton.isVisible() && addNewPolicyTemplateButton.isEnabled()) {
                addNewPolicyTemplateButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(10));
                addNewPolicyTemplateButton.click();
                logStep("Clicked '+ ADD NEW POLICY TEMPLATE' button (if applicable)");
            }
        } catch (Exception e) {
            throw new ElementException("Failed to click '+ ADD NEW POLICY TEMPLATE' button", e);
        }
    }
}
