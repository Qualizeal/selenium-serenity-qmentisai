package com.serenity.stepdefinition;

import com.serenity.pages.LoginPage;
import com.serenity.pages.TAPDealIntakeOpportunityPage;
import com.serenity.reporting.ReportManager;
import com.serenity.utilities.SerenityConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for the TAP Deal Intake Opportunity flow (TC_001_New TAP Deal Intake Opportunity_Deal Type - P&C).
 * Wires Cucumber steps to TAPDealIntakeOpportunityPage and LoginPage actions.
 *
 * Preconditions: None specified.
 *
 * @author QZ Automation
 */
public class TAPDealIntakeOpportunityStepDefinitions {

    private TAPDealIntakeOpportunityPage getTAPDealIntakeOpportunityPage() {
        return Serenity.getCurrentSession().containsKey("tapDealIntakeOpportunityPage")
                ? (TAPDealIntakeOpportunityPage) Serenity.getCurrentSession().get("tapDealIntakeOpportunityPage")
                : new TAPDealIntakeOpportunityPage();
    }

    private LoginPage getLoginPage() {
        return Serenity.getCurrentSession().containsKey("loginPage")
                ? (LoginPage) Serenity.getCurrentSession().get("loginPage")
                : new LoginPage();
    }

    @Given("the TAP user is logged in")
    public void theTAPUserIsLoggedIn() {
        ReportManager.recordStep("Logging in as TAP user");
        String username = SerenityConfigReader.getUsername();
        String password = SerenityConfigReader.getPassword();
        getLoginPage().login(username, password);
        getLoginPage().waitForLoginConfirmation();
        assertThat(getLoginPage().isLoginSuccessful())
                .as("TAP user should be successfully logged in")
                .isTrue();
        ReportManager.recordStep("TAP user logged in successfully");
    }

    @When("the TAP user navigates to Accounts -> Related -> Open Opportunities and clicks New")
    public void theTAPUserNavigatesToAccountsRelatedOpenOpportunitiesAndClicksNew() {
        ReportManager.recordStep("Navigating to Accounts > Related > Open Opportunities and clicking New");
        getTAPDealIntakeOpportunityPage().navigateToAccountsRelatedOpenOpportunities();
        getTAPDealIntakeOpportunityPage().clickNewOpportunityButton();
    }

    @And("selects TAP Deal Intake and Next")
    public void selectsTAPDealIntakeAndNext() {
        ReportManager.recordStep("Selecting TAP Deal Intake and clicking Next");
        getTAPDealIntakeOpportunityPage().selectTAPDealIntakeRadioAndNext();
    }

    @And("fills Deal Type as {string}, Primary Producer as {string}, Opportunity Name as {string}, Deal Structure as {string}")
    public void fillsDealTypePrimaryProducerOpportunityNameDealStructure(String dealType, String primaryProducer, String opportunityName, String dealStructure) {
        ReportManager.recordStep(String.format("Filling Deal Type: %s, Primary Producer: %s, Opportunity Name: %s, Deal Structure: %s", dealType, primaryProducer, opportunityName, dealStructure));
        getTAPDealIntakeOpportunityPage().selectDealType(dealType);
        getTAPDealIntakeOpportunityPage().searchAndSelectPrimaryProducer(primaryProducer);
        getTAPDealIntakeOpportunityPage().enterOpportunityName(opportunityName);
        getTAPDealIntakeOpportunityPage().selectDealStructure(dealStructure);
    }

    @And("selects Deal Overview options: PC Strategy as {string}, People Solutions Strategy as {string}")
    public void selectsDealOverviewOptions(String pcStrategy, String peopleSolutionsStrategy) {
        ReportManager.recordStep(String.format("Selecting Deal Overview options: PC Strategy: %s, People Solutions Strategy: %s", pcStrategy, peopleSolutionsStrategy));
        getTAPDealIntakeOpportunityPage().selectPCStrategy(pcStrategy);
        getTAPDealIntakeOpportunityPage().selectPeopleSolutionsStrategy(peopleSolutionsStrategy);
    }

    @And("fills Sign Date as {string}, Buyer as {string}, Target as {string}, Target Close Date as {string}, Reps\/Warranty as {string}, Employees as {string}, Revenue as {string}")
    public void fillsSignDateBuyerTargetTargetCloseDateRepsWarrantyEmployeesRevenue(String signDate, String buyer, String target, String targetCloseDate, String repsWarranty, String employees, String revenue) {
        ReportManager.recordStep("Filling Sign Date, Buyer, Target, Target Close Date, Reps/Warranty, Employees, Revenue");
        getTAPDealIntakeOpportunityPage().selectSignDate(signDate);
        getTAPDealIntakeOpportunityPage().searchAndSelectBuyer(buyer);
        getTAPDealIntakeOpportunityPage().searchAndSelectTarget(target);
        getTAPDealIntakeOpportunityPage().selectTargetCloseDate(targetCloseDate);
        getTAPDealIntakeOpportunityPage().selectRepsWarranty(repsWarranty);
        getTAPDealIntakeOpportunityPage().enterNumberOfEmployees(employees);
        getTAPDealIntakeOpportunityPage().enterRevenue(revenue);
    }

    @And("selects Diligence Fee Agreement as {string}, LOI Signed as {string}, Stage as {string}, Deliverable Due Date as {string}")
    public void selectsDiligenceFeeAgreementLOISignedStageDeliverableDueDate(String diligenceFeeAgreement, String loiSigned, String stage, String deliverableDueDate) {
        ReportManager.recordStep("Selecting Diligence Fee Agreement, LOI Signed, Stage, Deliverable Due Date");
        getTAPDealIntakeOpportunityPage().selectDiligenceFeeAgreement(diligenceFeeAgreement);
        getTAPDealIntakeOpportunityPage().selectLOISigned(loiSigned);
        getTAPDealIntakeOpportunityPage().selectStage(stage);
        getTAPDealIntakeOpportunityPage().selectDeliverableDueDate(deliverableDueDate);
    }

    @And("clicks Save and validates the created Opportunity and Stage progression")
    public void clicksSaveAndValidatesTheCreatedOpportunityAndStageProgression() {
        ReportManager.recordStep("Clicking Save and validating Opportunity creation and stage progression");
        String createdOpportunityName = getTAPDealIntakeOpportunityPage().clickSaveAndGetOpportunityName();
        assertThat(createdOpportunityName)
                .as("Opportunity should be created and name should not be empty")
                .isNotBlank();
        ReportManager.recordStep("Opportunity created: " + createdOpportunityName);
        assertThat(getTAPDealIntakeOpportunityPage().isOnlyOneChevronPathDisplayed())
                .as("Only one path of chevrons should be displayed")
                .isTrue();
        String[] expectedStages = {"Intake", "Pending VDR Access", "Data Gathering", "Engage Account Team", "Write Report", "Report Delivered", "Closed Won", "Closed Lost"};
        assertThat(getTAPDealIntakeOpportunityPage().getOpportunityStages())
                .containsExactly(expectedStages);
        for (String stage : expectedStages) {
            getTAPDealIntakeOpportunityPage().clickStage(stage);
            assertThat(getTAPDealIntakeOpportunityPage().getCurrentStage()).isEqualTo(stage);
            ReportManager.recordStep("Stage changed to: " + stage);
        }
    }

    // Optionally, add hooks for before/after scenario for reporting if needed
}
