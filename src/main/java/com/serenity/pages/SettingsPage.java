package com.serenity.pages;
import com.serenity.base.BasePage;
import com.serenity.exceptions.ElementException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import java.time.Duration;

public class SettingsPage extends BasePage {
    @FindBy(xpath = "//a[normalize-space()='Investment Policy Templates']")
    private WebElementFacade investmentPolicyTemplatesLink;
    
    @FindBy(xpath = "//button[@id='investmentPolicyTemplateList_paging_next']")
    private WebElementFacade nextPageButton;
    
    @FindBy(xpath = "//button[@id='investmentPolicyTemplateList_pagesize_100']")
    private WebElementFacade hundredRowsButton;
    
    @FindBy(xpath = "//span[text()='View Template']")
    private WebElementFacade viewTemplateLink;

    // Locators for Application section and Investment Policy Templates link in left navigation
    private static final By APPLICATION_SECTION = By.xpath("//a[normalize-space()='Application']");
    private static final By INVESTMENT_POLICY_TEMPLATES_LINK = By.xpath("//a[normalize-space()='Investment Policy Templates']");

    /**
     * Navigates to the Investment Policy Templates page from the Workspace Setup context.
     * Ensures the Application section is visible and expanded, then clicks the Investment Policy Templates link.
     */
    public void navigateToInvestmentPolicyTemplates() {
        try {
            // Wait for the Application section to be visible and clickable
            WebElementFacade applicationSection = find(APPLICATION_SECTION);
            applicationSection.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(30));
            if (!applicationSection.getAttribute("class").contains("active")) {
                applicationSection.click();
                logStep("Clicked Application section in left navigation to expand it");
                waitABit(1000); // Wait for submenu to expand
            }

            // Wait for the Investment Policy Templates link to be visible and clickable
            WebElementFacade iptLink = find(INVESTMENT_POLICY_TEMPLATES_LINK);
            iptLink.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(30));
            iptLink.click();
            logStep("Clicked Investment Policy Templates link under Application section");
            // Wait for navigation to complete
            waitABit(2000);
        } catch (Exception e) {
            throw new ElementException("Failed to navigate to Investment Policy Templates from Workspace Setup", e);
        }
    }

    /**
     * Checks if the Investment Policy Templates link is visible under the Application section in the left navigation.
     * @return true if visible, false otherwise
     */
    public boolean isInvestmentPolicyTemplatesVisible() {
        try {
            WebElementFacade applicationSection = find(APPLICATION_SECTION);
            applicationSection.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(10));
            if (!applicationSection.getAttribute("class").contains("active")) {
                applicationSection.click();
                waitABit(500);
            }
            WebElementFacade iptLink = find(INVESTMENT_POLICY_TEMPLATES_LINK);
            return iptLink.isVisible();
        } catch (Exception e) {
            logStep("Investment Policy Templates link not visible: " + e.getMessage());
            return false;
        }
    }

    public void clickInvestmentPolicyTemplatesLink() {
        try {
            investmentPolicyTemplatesLink.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(60));
            investmentPolicyTemplatesLink.click();
            logStep("Clicked investment policy templates link");
        } catch (Exception e) {
            throw new ElementException("Failed to click investment policy templates link", e);
        }
    }
    
    /**
     * Searches for the specified template through pagination and clicks it if found.
     * First sets the page size to 100 rows, then iterates through pages until the element is found.
     * @param templateName - the name of the template to search for
     * @throws ElementException if template is not found after checking all pages
     */
    public void findAndClickTemplate(String templateName) {
        try {
            // First, try to set page size to 100 rows to maximize visibility
            try {
                if (hundredRowsButton.isVisible() && hundredRowsButton.isClickable()) {
                    hundredRowsButton.click();
                    logStep("Clicked 100 rows button to display maximum rows per page");
                    // Wait for the page to refresh after changing page size
                    waitABit(2000);
                }
            } catch (Exception e) {
                logStep("100 rows button not available or already selected, continuing with current page size");
            }
            
            int maxPages = 50; // Maximum number of pages to check to avoid infinite loop
            int currentPage = 0;
            boolean found = false;
            
            while (currentPage < maxPages && !found) {
                currentPage++;
                logStep("Checking page " + currentPage + " for " + templateName);
                
                // Create dynamic XPath using the template name from feature file
                String dynamicXpath = "//span[text()='" + templateName + "']";
                
                // Check if the element is present on current page
                try {
                    WebElementFacade templateLink = find(By.xpath(dynamicXpath));
                    if (templateLink.isVisible()) {
                        logStep("Found " + templateName + " on page " + currentPage);
                        templateLink.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(10));
                        templateLink.click();
                        logStep("Clicked on " + templateName);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    // Element not visible on current page, continue to next page
                    logStep(templateName + " not found on page " + currentPage);
                }
                
                // Try to click Next button to go to next page
                try {
                    if (nextPageButton.isVisible() && nextPageButton.isEnabled()) {
                        nextPageButton.click();
                        logStep("Clicked Next button to navigate to next page");
                        // Wait for the page to load
                        waitABit(2000);
                    } else {
                        logStep("Next button is not available - reached last page");
                        break;
                    }
                } catch (Exception e) {
                    logStep("Next button is not available or disabled - reached last page");
                    break;
                }
            }
            
            if (!found) {
                throw new ElementException(templateName + " not found after checking " + currentPage + " pages");
            }
            
        } catch (ElementException e) {
            throw e; // Re-throw ElementException as is
        } catch (Exception e) {
            throw new ElementException("Failed to find and click " + templateName, e);
        }
    }
    
}
