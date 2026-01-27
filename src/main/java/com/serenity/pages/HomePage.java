package com.serenity.pages;
import com.serenity.base.BasePage;
import com.serenity.exceptions.ElementException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import java.time.Duration;

public class HomePage extends BasePage {
    @FindBy(xpath = "//i[@icon=\"'settings'\"]")
    private WebElementFacade settingsButton;


    public void navigateToSettings() {
    
        try {
            settingsButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(30));
            settingsButton.click();
            logStep("Clicked settings button");
        } catch (Exception e) {
            throw new ElementException("Failed to click Settings button", e);
        }
    }
}
