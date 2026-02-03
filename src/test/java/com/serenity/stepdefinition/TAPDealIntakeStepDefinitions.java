package com.serenity.stepdefinition;

import com.serenity.pages.LoginPage;
import com.serenity.pages.TAPHomePage;
import com.serenity.pages.TAPAccountPage;
import com.serenity.pages.TAPOpportunityPage;
import com.serenity.pages.TAPDealIntakePage;
import com.serenity.reporting.ReportManager;
import com.serenity.utilities.TAPDataLoader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import org.assertj.core.api.Assertions;

public class TAPDealIntakeStepDefinitions {
    private LoginPage getLoginPage() {
        return Serenity.getCurrentSession().containsKey("loginPage") ?
                (LoginPage) Serenity.getCurrentSession().get("loginPage") : new LoginPage();
    }

    private TAPHomePage getTAPHomePage() {
        return Serenity.getCurrentSession().containsKey("tapHomePage") ?
                (TAPHomePage) Serenity.getCurrentSession().get("tapHomePage") : new TAPHomePage();
    }

    private TAPAccountPage getTAPAccountPage() {
        return Serenity.getCurrentSession().containsKey("tapAccountPage") ?
                (TAPAccountPage) Serenity.getCurrentSession().get("tapAccountPage") : new TAPAccountPage();
    }

    private TAPOpportunityPage getTAPOpportunityPage() {
        return Serenity.getCurrentSession().containsKey("tapOpportunityPage") ?
                (TAPOpportunityPage) Serenity.getCurrentSession().get("tapOpportunityPage") : new TAPOpportunityPage();
    }

    private TAPDealIntakePage getTAPDealIntakePage() {
        return Serenity.getCurrentSession().containsKey("tapDealIntakePage") ?
                (TAPDealIntakePage) Serenity.getCurrentSession().get("tapDealIntakePage") : new TAPDealIntakePage();
    }

    private TAPDataLoader getTAPDataLoader() {
        return Serenity.getCurrentSession().containsKey("tapDataLoader") ?
                (TAPDataLoader) Serenity.getCurrentSession().get("tapDataLoader") : new TAPDataLoader();
    }

    @Given("^Log in to Salesforce using valid credentials$")
    public void loginToSalesforce() {
        String username = getTAPDataLoader().getValidUser().getUsername();
        String password = getTAPDataLoader().getValidUser().getPassword();
        getLoginPage().login(username, password);
        Assertions.assertThat(getLoginPage().isLoginSuccessful())
                .as("User should be successfully logged in to Salesforce")
                .isTrue();
        ReportManager.recordStep("User logged in to Salesforce as " + username);
    }

    @Then("^Click on the Setup icon and search for a TAP Profile User \(e.g., Dillon Cox)$")
    public void searchForTAPProfileUser() {
        String tapUser = getTAPDataLoader().getTestData("tapProfileUser");
        getTAPHomePage().clickSetupIcon();
        getTAPHomePage().searchUser(tapUser);
        Assertions.assertThat(getTAPHomePage().isOnUserProfilePage(tapUser))
                .as("Should be on TAP Profile User page for " + tapUser)
                .isTrue();
        ReportManager.recordStep("Navigated to TAP Profile User page for " + tapUser);
    }

    @And("^Click on the Login button for the selected TAP user$")
    public void loginAsTAPUser() {
        getTAPHomePage().clickLoginAsUser();
        Assertions.assertThat(getTAPHomePage().isLoggedInAsTAPUser())
                .as("Should be logged in as TAP user")
                .isTrue();
        ReportManager.recordStep("Logged in as TAP user");
    }

    @Then("^Click on the Home dropdown and select the Accounts option$")
    public void selectAccountsFromHomeDropdown() {
        getTAPHomePage().openHomeDropdown();
        getTAPHomePage().selectAccountsOption();
        Assertions.assertThat(getTAPAccountPage().isOnRecentlyViewedAccountsPage())
                .as("Should be on Recently Viewed Accounts page")
                .isTrue();
        ReportManager.recordStep("Navigated to Recently Viewed Accounts page");
    }

    @Then("^Select an account with Account Record Type = Client \(e.g., 1031 Exchange Corporation)$")
    public void selectClientAccount() {
        String accountName = getTAPDataLoader().getTestData("clientAccountName");
        getTAPAccountPage().selectAccountByName(accountName);
        Assertions.assertThat(getTAPAccountPage().isOnAccountDetailsPage(accountName))
                .as("Should be on selected Account details page")
                .isTrue();
        ReportManager.recordStep("Navigated to Account details page for " + accountName);
    }

    @Then("^Click on the Related tab on the Account page$")
    public void clickRelatedTabOnAccountPage() {
        getTAPAccountPage().clickRelatedTab();
        Assertions.assertThat(getTAPAccountPage().isRelatedTabActive())
                .as("Related tab should be active")
                .isTrue();
        ReportManager.recordStep("Related tab is active on Account page");
    }

    @Then("^Click on the New button beside the Open Opportunities related list$")
    public void clickNewOpportunityButton() {
        getTAPAccountPage().clickNewOpportunity();
        Assertions.assertThat(getTAPOpportunityPage().isOnNewOpportunityPage())
                .as("Should be on New Opportunity creation page")
                .isTrue();
        ReportManager.recordStep("Navigated to New Opportunity creation page");
    }

    @Then("^Select the TAP Deal Intake radio button and click on Next$")
    public void selectTAPDealIntakeRadioAndNext() {
        getTAPOpportunityPage().selectTAPDealIntakeRadio();
        getTAPOpportunityPage().clickNextButton();
        Assertions.assertThat(getTAPDealIntakePage().isOnTAPDealIntakeHeader())
                .as("Page header should be 'New Opportunity: TAP Deal Intake'")
                .isTrue();
        ReportManager.recordStep("Selected TAP Deal Intake and navigated to intake form");
    }

    @Then("^Select P&C from the Deal Type field$")
    public void selectDealTypePC() {
        getTAPDealIntakePage().selectDealType("P&C");
        Assertions.assertThat(getTAPDealIntakePage().getSelectedDealType()).isEqualTo("P&C");
        ReportManager.recordStep("Selected Deal Type: P&C");
    }

    @Then("^Search and select the required producer in the Primary Producer field$")
    public void selectPrimaryProducer() {
        String producer = getTAPDataLoader().getTestData("primaryProducer");
        getTAPDealIntakePage().searchAndSelectPrimaryProducer(producer);
        Assertions.assertThat(getTAPDealIntakePage().getSelectedPrimaryProducer()).isEqualTo(producer);
        ReportManager.recordStep("Selected Primary Producer: " + producer);
    }

    @Then("^Enter valid data in the Opportunity Name field$")
    public void enterOpportunityName() {
        String opportunityName = getTAPDataLoader().getTestData("opportunityName");
        getTAPDealIntakePage().enterOpportunityName(opportunityName);
        Assertions.assertThat(getTAPDealIntakePage().getOpportunityName()).isEqualTo(opportunityName);
        ReportManager.recordStep("Entered Opportunity Name: " + opportunityName);
    }

    @Then("^Select the required option from the Deal Structure field$")
    public void selectDealStructure() {
        String dealStructure = getTAPDataLoader().getTestData("dealStructure");
        getTAPDealIntakePage().selectDealStructure(dealStructure);
        Assertions.assertThat(getTAPDealIntakePage().getSelectedDealStructure()).isEqualTo(dealStructure);
        ReportManager.recordStep("Selected Deal Structure: " + dealStructure);
    }

    @Then("^Select the required P&C Strategy \(if applicable) from Available and move it to Chosen$")
    public void selectPCStrategy() {
        String pcStrategy = getTAPDataLoader().getTestData("pcStrategy");
        getTAPDealIntakePage().moveStrategyToChosen("P&C", pcStrategy);
        Assertions.assertThat(getTAPDealIntakePage().isStrategyChosen("P&C", pcStrategy)).isTrue();
        ReportManager.recordStep("Selected P&C Strategy: " + pcStrategy);
    }

    @Then("^Select the required People Solutions Strategy \(if applicable) from Available and move it to Chosen$")
    public void selectPeopleSolutionsStrategy() {
        String psStrategy = getTAPDataLoader().getTestData("peopleSolutionsStrategy");
        getTAPDealIntakePage().moveStrategyToChosen("People Solutions", psStrategy);
        Assertions.assertThat(getTAPDealIntakePage().isStrategyChosen("People Solutions", psStrategy)).isTrue();
        ReportManager.recordStep("Selected People Solutions Strategy: " + psStrategy);
    }

    @Then("^Select a valid Sign Date$")
    public void selectSignDate() {
        String signDate = getTAPDataLoader().getTestData("signDate");
        getTAPDealIntakePage().selectSignDate(signDate);
        Assertions.assertThat(getTAPDealIntakePage().getSignDate()).isEqualTo(signDate);
        ReportManager.recordStep("Selected Sign Date: " + signDate);
    }

    @Then("^Click on the Buyer lookup field and select the required Buyer Account$")
    public void selectBuyerAccount() {
        String buyerAccount = getTAPDataLoader().getTestData("buyerAccount");
        getTAPDealIntakePage().selectBuyerAccount(buyerAccount);
        Assertions.assertThat(getTAPDealIntakePage().getBuyerAccount()).isEqualTo(buyerAccount);
        ReportManager.recordStep("Selected Buyer Account: " + buyerAccount);
    }

    @Then("^Click on the Target lookup field and select the required Target Account$")
    public void selectTargetAccount() {
        String targetAccount = getTAPDataLoader().getTestData("targetAccount");
        getTAPDealIntakePage().selectTargetAccount(targetAccount);
        Assertions.assertThat(getTAPDealIntakePage().getTargetAccount()).isEqualTo(targetAccount);
        ReportManager.recordStep("Selected Target Account: " + targetAccount);
    }

    @Then("^Select a valid Target Close Date$")
    public void selectTargetCloseDate() {
        String closeDate = getTAPDataLoader().getTestData("targetCloseDate");
        getTAPDealIntakePage().selectTargetCloseDate(closeDate);
        Assertions.assertThat(getTAPDealIntakePage().getTargetCloseDate()).isEqualTo(closeDate);
        ReportManager.recordStep("Selected Target Close Date: " + closeDate);
    }

    @Then("^Select the required option from the Reps & Warranty\? field$")
    public void selectRepsAndWarranty() {
        String repsWarranty = getTAPDataLoader().getTestData("repsWarranty");
        getTAPDealIntakePage().selectRepsWarranty(repsWarranty);
        Assertions.assertThat(getTAPDealIntakePage().getSelectedRepsWarranty()).isEqualTo(repsWarranty);
        ReportManager.recordStep("Selected Reps & Warranty: " + repsWarranty);
    }

    @Then("^Enter a valid value in the Number of Employees field$")
    public void enterNumberOfEmployees() {
        String numEmployees = getTAPDataLoader().getTestData("numberOfEmployees");
        getTAPDealIntakePage().enterNumberOfEmployees(numEmployees);
        Assertions.assertThat(getTAPDealIntakePage().getNumberOfEmployees()).isEqualTo(numEmployees);
        ReportManager.recordStep("Entered Number of Employees: " + numEmployees);
    }

    @Then("^Enter a valid value in the Revenue field$")
    public void enterRevenue() {
        String revenue = getTAPDataLoader().getTestData("revenue");
        getTAPDealIntakePage().enterRevenue(revenue);
        Assertions.assertThat(getTAPDealIntakePage().getRevenue()).isEqualTo(revenue);
        ReportManager.recordStep("Entered Revenue: " + revenue);
    }

    @Then("^Select the required option from the Diligence Fee Agreement field$")
    public void selectDiligenceFeeAgreement() {
        String diligenceFee = getTAPDataLoader().getTestData("diligenceFeeAgreement");
        getTAPDealIntakePage().selectDiligenceFeeAgreement(diligenceFee);
        Assertions.assertThat(getTAPDealIntakePage().getSelectedDiligenceFeeAgreement()).isEqualTo(diligenceFee);
        ReportManager.recordStep("Selected Diligence Fee Agreement: " + diligenceFee);
    }

    @Then("^Select the required option from the LOI Signed\? field$")
    public void selectLOISigned() {
        String loiSigned = getTAPDataLoader().getTestData("loiSigned");
        getTAPDealIntakePage().selectLOISigned(loiSigned);
        Assertions.assertThat(getTAPDealIntakePage().getSelectedLOISigned()).isEqualTo(loiSigned);
        ReportManager.recordStep("Selected LOI Signed: " + loiSigned);
    }

    @Then("^Select the required option from the Stage field$")
    public void selectStage() {
        String stage = getTAPDataLoader().getTestData("stage");
        getTAPDealIntakePage().selectStage(stage);
        Assertions.assertThat(getTAPDealIntakePage().getSelectedStage()).isEqualTo(stage);
        ReportManager.recordStep("Selected Stage: " + stage);
    }

    @Then("^Select a valid Deliverable Due Date$")
    public void selectDeliverableDueDate() {
        String deliverableDueDate = getTAPDataLoader().getTestData("deliverableDueDate");
        getTAPDealIntakePage().selectDeliverableDueDate(deliverableDueDate);
        Assertions.assertThat(getTAPDealIntakePage().getDeliverableDueDate()).isEqualTo(deliverableDueDate);
        ReportManager.recordStep("Selected Deliverable Due Date: " + deliverableDueDate);
    }

    @Then("^Click on the Save button$")
    public void clickSaveButton() {
        getTAPDealIntakePage().clickSaveButton();
        String opportunityName = getTAPDealIntakePage().getOpportunityName();
        Assertions.assertThat(getTAPDealIntakePage().isSuccessMessageDisplayed(opportunityName))
                .as("Success message should be displayed for created opportunity")
                .isTrue();
        ReportManager.recordStep("Opportunity '" + opportunityName + "' was created.");
    }

    @Then("^Verify that only one chevron path is displayed for the Opportunity$")
    public void verifySingleChevronPath() {
        Assertions.assertThat(getTAPDealIntakePage().isSingleChevronPathDisplayed())
                .as("Only a single chevron path should be displayed")
                .isTrue();
        ReportManager.recordStep("Verified only one chevron path is displayed");
    }

    @Then("^Verify that the Opportunity stages are displayed as: Intake, Pending VDR Access, Data Gathering, Engage Account Team, Write Report, Report Delivered, Closed Won, Closed Lost$")
    public void verifyOpportunityStages() {
        String[] expectedStages = {"Intake", "Pending VDR Access", "Data Gathering", "Engage Account Team", "Write Report", "Report Delivered", "Closed Won", "Closed Lost"};
        Assertions.assertThat(getTAPDealIntakePage().getDisplayedStages())
                .containsExactly(expectedStages);
        ReportManager.recordStep("Verified all listed opportunity stages are displayed");
    }

    @Then("^Click on each stage chevron and verify the Opportunity stage updates accordingly$")
    public void clickEachStageChevronAndVerify() {
        String[] stages = {"Intake", "Pending VDR Access", "Data Gathering", "Engage Account Team", "Write Report", "Report Delivered", "Closed Won", "Closed Lost"};
        for (String stage : stages) {
            getTAPDealIntakePage().clickStageChevron(stage);
            Assertions.assertThat(getTAPDealIntakePage().getCurrentStage()).isEqualTo(stage);
            ReportManager.recordStep("Stage changed to: " + stage);
        }
    }
}
