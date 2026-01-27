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
