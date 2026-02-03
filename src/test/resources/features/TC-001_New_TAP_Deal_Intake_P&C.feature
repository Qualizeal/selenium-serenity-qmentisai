Feature: TC-001 New TAP Deal Intake Opportunity - Deal Type P&C
  As a Salesforce TAP Profile User
  I want to create a new TAP Deal Intake Opportunity with Deal Type P&C
  So that I can validate the end-to-end TAP Deal Intake workflow

  Background:
    Given The user has valid Salesforce credentials
    And A TAP Profile User exists in the system

  @TC-001 @TAP @DealIntake @PAndC @Regression
  Scenario: Create a new TAP Deal Intake Opportunity with Deal Type P&C
    Given The user logs in to Salesforce with valid credentials
    When The user clicks on the Setup icon and searches for TAP Profile User "Dillon Cox"
    And The user logs in as the selected TAP Profile User
    And The user clicks on the Home dropdown and selects the Accounts option
    And The user is redirected to the Recently Viewed Accounts page
    And The user selects the account with Account Record Type "Client" named "1031 Exchange Corporation"
    And The user navigates to the Related tab on the Account details page
    And The user clicks the New button beside the Open Opportunities related list
    And The user selects the "TAP Deal Intake" radio button and clicks Next
    And The user selects "P&C" from the Deal Type field
    And The user searches and selects "<Primary Producer>" in the Primary Producer field
    And The user enters "<Opportunity Name>" in the Opportunity Name field
    And The user selects "<Deal Structure>" from the Deal Structure field
    And The user selects the required P&C Strategy from Available and moves it to Chosen
    And The user selects the required People Solutions Strategy from Available and moves it to Chosen
    And The user selects a valid Sign Date "<Sign Date>"
    And The user selects the required Buyer Account in the Buyer lookup field
    And The user selects the required Target Account in the Target lookup field
    And The user selects a valid Target Close Date "<Target Close Date>"
    And The user selects the required option from the Reps & Warranty? field
    And The user enters a valid value "<Number of Employees>" in the Number of Employees field
    And The user enters a valid value "<Revenue>" in the Revenue field
    And The user selects the required option from the Diligence Fee Agreement field
    And The user selects the required option from the LOI Signed? field
    And The user selects the required Stage "<Stage>" from the Stage field
    And The user selects a valid Deliverable Due Date "<Deliverable Due Date>"
    And The user clicks the Save button
    Then A success message is displayed with text: "Opportunity '<Opportunity Name>' was created."
    And Only one chevron path is displayed for the Opportunity
    And The Opportunity stages are displayed as:
      | Intake              |
      | Pending VDR Access  |
      | Data Gathering      |
      | Engage Account Team |
      | Write Report        |
      | Report Delivered    |
      | Closed Won          |
      | Closed Lost         |
    And The user clicks on each stage chevron and verifies the Opportunity stage updates accordingly
    And The Opportunity is created and visible in the system

  Examples:
    | Primary Producer | Opportunity Name         | Deal Structure | P&C Strategy | People Solutions Strategy | Sign Date  | Buyer Account | Target Account | Target Close Date | Number of Employees | Revenue | Stage   | Deliverable Due Date |
    | John Producer    | TAP P&C Intake Opp 001   | Structure A    | Add-On       | Carveout                | 2024-07-01 | Buyer Corp    | Target Corp    | 2024-12-31        | 250                 | 1000000 | Intake  | 2024-08-15           |
