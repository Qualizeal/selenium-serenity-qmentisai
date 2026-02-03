package com.serenity.pages;

import com.serenity.base.BasePage;
import com.serenity.exceptions.ElementException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import java.time.Duration;
import java.util.List;

/**
 * Page Object for Salesforce TAP Profile User search and Login-As-User actions.
 * Supports searching for TAP Profile users (e.g., Dillon Cox) and logging in as them.
 */
public class TAPProfilePage extends BasePage {

    private static final By USER_ROW_BY_NAME = By.xpath("//table//tr[.//a[contains(text(),'%s')]]");
    private static final By LOGIN_BUTTON_IN_ROW = By.xpath(".//a[contains(@title,'Log in to') or contains(text(),'Log in as this user') or contains(text(),'Login')] | .//input[@value='Login']");

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or @type='search' or @title='Search']")
    private WebElementFacade searchInput;

    @FindBy(xpath = "//button[contains(@title,'Search') or contains(@aria-label,'Search') or contains(text(),'Search')]")
    private WebElementFacade searchButton;

    @FindBy(xpath = "//iframe[contains(@title,'User') or contains(@name,'vfFrame') or contains(@src,'User')] | //iframe[contains(@title,'Visualforce')]")
    private List<WebElementFacade> userIframes;

    /**
     * Searches for a TAP Profile User by name in the Setup Users page.
     * @param userName The full or partial name of the TAP Profile user (e.g., "Dillon Cox").
     */
    public void searchTapProfileUser(String userName) {
        try {
            waitForPageLoad();
            if (searchInput.isVisible()) {
                searchInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
                searchInput.clear();
                searchInput.type(userName);
                if (searchButton.isVisible()) {
                    searchButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
                    searchButton.click();
                } else {
                    searchInput.sendKeys("\n");
                }
            }
            waitABit(2000); // Wait for results to load
        } catch (Exception e) {
            throw new ElementException("Failed to search TAP Profile User: " + userName, e);
        }
    }

    /**
     * Logs in as the specified TAP Profile User by searching and clicking the Login button.
     * @param userName The full name of the TAP Profile user (e.g., "Dillon Cox").
     */
    public void loginAsProfileUser(String userName) {
        try {
            searchTapProfileUser(userName);
            boolean switchedToUserFrame = false;
            // If the user table is inside an iframe, switch to it
            if (!userIframes.isEmpty()) {
                for (WebElementFacade frame : userIframes) {
                    try {
                        getDriver().switchTo().frame(frame);
                        switchedToUserFrame = true;
                        break;
                    } catch (Exception ignored) {}
                }
            }
            String userRowXpath = String.format(USER_ROW_BY_NAME.toString().replace("By.xpath: ", ""), userName);
            WebElementFacade userRow = find(By.xpath(userRowXpath));
            userRow.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            WebElementFacade loginButton = userRow.then(LOGIN_BUTTON_IN_ROW);
            loginButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(DEFAULT_TIMEOUT));
            loginButton.click();
            waitForPageLoad();
            if (switchedToUserFrame) {
                getDriver().switchTo().defaultContent();
            }
            waitABit(3000); // Wait for login-as-user to complete
        } catch (Exception e) {
            throw new ElementException("Failed to login as TAP Profile User: " + userName, e);
        }
    }

    /**
     * Verifies if currently logged in as the specified TAP Profile User.
     * @param userName The expected user name.
     * @return true if logged in as the user, false otherwise.
     */
    public boolean isLoggedInAsUser(String userName) {
        try {
            // Salesforce typically displays the user name in the top right or in a profile menu
            By userNameLocator = By.xpath("//span[contains(@class,'userProfileCardTriggerRoot') or contains(@class,'profileTrigger') or contains(@class,'profile-card') or contains(@class,'profileName')][contains(text(), '" + userName + "')]");
            return find(userNameLocator).isVisible();
        } catch (Exception e) {
            return false;
        }
    }
}
