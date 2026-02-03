Feature: TC_001_New TAP Deal Intake Opportunity - Deal Type: P&C
  As a TAP user
  I want to create a new TAP Deal Intake Opportunity with Deal Type P&C
  So that I can track and manage the opportunity lifecycle in Salesforce

  # Test data for this scenario is driven from testData_TAPDealIntake.json
  # Placeholders in angle brackets (<...>) are to be referenced by data from the test data file or DataTable

  @TAP @Opportunity @P&C @TC_186466
  Scenario Outline: Create a new TAP Deal Intake Opportunity for Deal Type P&C and validate opportunity stages
    Given the user logs into Salesforce with valid credentials from "testData_TAPDealIntake.json" as <tapUser>
    And the user logs in as TAP user <tapUser>
    When the user selects "Accounts" from the Home dropdown
    And the user selects an Account with Record Type "Client" named <accountName>
    And the user navigates to the "Related" tab on the Account page
    And the user clicks the "New" button in the 'Open Opportunities' related list
    And the user selects the "TAP Deal Intake" radio button and clicks "Next"
    And the user selects <dealType> from the "Deal Type" field
    And the user searches and selects <primaryProducer> in the "Primary Producer" field
    And the user enters <opportunityName> in the "Opportunity Name" field
    And the user selects <dealStructure> from the "Deal Structure" field
    And the user selects <pcStrategy> from the "P&C Strategy" available to chosen box if applicable
    And the user selects <peopleSolutionsStrategy> from the "People Solutions Strategy" available to chosen box if applicable
    And the user selects <signDate> as the "Sign Date"
    And the user selects <buyerAccount> in the "Buyer" search field
    And the user selects <targetAccount> in the "Target" search field
    And the user selects <targetCloseDate> as the "Target Close Date"
    And the user selects <repsWarranty> from the "Reps & Warranty?" field
    And the user enters <numberOfEmployees> in the "Number of Employees" field
    And the user enters <revenue> in the "Revenue" field
    And the user selects <diligenceFeeAgreement> from the "Diligence Fee Agreement" field
    And the user selects <loiSigned> from the "LOI Signed?" field
    And the user selects <stage> from the "Stage" field
    And the user selects <deliverableDueDate> as the "Deliverable Due Date"
    And the user clicks the "Save" button
    Then the user should see a success message confirming the Opportunity <opportunityName> was created
    And only one path of chevron should be displayed
    And the Opportunity stages should be:
      | Intake                |
      | Pending VDR Access    |
      | Data Gathering        |
      | Engage Account Team   |
      | Write Report          |
      | Report Delivered      |
      | Closed Won            |
      | Closed Lost           |
    When the user clicks each stage chevron
    Then the stage of the opportunity should update accordingly

    Examples:
      | tapUser           | accountName             | dealType | primaryProducer | opportunityName   | dealStructure | pcStrategy | peopleSolutionsStrategy | signDate    | buyerAccount   | targetAccount   | targetCloseDate | repsWarranty | numberOfEmployees | revenue  | diligenceFeeAgreement | loiSigned | stage  | deliverableDueDate |
      | tap.user@test.com | Hecht Partners, LLP     | P&C      | John Doe        | P&C Deal 2024     | Platform      | Add-On     | Platform                | 2024-06-10  | Buyer LLC      | Target Inc      | 2024-07-01      | Yes         | 200               | 5000000  | Yes                  | No        | Intake | 2024-07-15        |

  # All steps are mapped to TAPDealIntakeOpportunityStepDefinitions for implementation.
