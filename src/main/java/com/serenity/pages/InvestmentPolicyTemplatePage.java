package com.serenity.pages;

import com.serenity.base.BasePage;
import com.serenity.exceptions.ElementException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import java.time.Duration;

/**
 * InvestmentPolicyTemplatePage represents the Investment Policy Template page.
 * Contains all locators and actions related to editing and updating investment policy templates.
 */
public class InvestmentPolicyTemplatePage extends BasePage {

    @FindBy(xpath = "//form[@id='template-update']")
    private WebElementFacade templateUpdateForm;

    @FindBy(xpath = "//body[@id='tinymce']")
    private WebElementFacade tinymceTextArea;

    @FindBy(xpath = "//button[text()='Update']")
    private WebElementFacade updateButton;

    @FindBy(xpath = "//button[text()='Make Update']")
    private WebElementFacade makeUpdateButton;

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

    
}
