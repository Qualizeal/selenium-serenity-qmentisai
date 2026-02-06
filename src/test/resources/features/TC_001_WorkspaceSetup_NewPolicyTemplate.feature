Feature: TC_001_Validate user can navigate to Workspace Setup and open New Investment Policy Template panel
  As a Fi360 Platform user
  I want to navigate to Workspace Setup and open the New Investment Policy Template panel
  So that I can create a new Investment Policy Template with required fields and options

  @TC_001 @WorkspaceSetup @InvestmentPolicyTemplate @Smoke
  Scenario: Validate user can navigate to Workspace Setup and open New Investment Policy Template panel
    Given The user is logged into the Cloud Platform application
    Then The Fi360 Home page is displayed with welcome message and application tiles
    Then Click on Settings from top right corner
    Then The Workspace Setup page is displayed with Application, Access Workspace, and Books of Business tabs
    Then Navigate to Investment Policy Templates on Settings page
    Then The Investment Policy Templates list page is displayed with columns Name, Type, Status, Date Created, and Last Modified
    Then Click on "+ ADD NEW POLICY TEMPLATE" button
    Then The New Investment Policy Template panel is displayed
    And The panel contains the field "Template Name"
    And The panel contains Asset Allocation Type options:
      | With Targets & Ranges     |
      | Peer Group Name Only      |
      | None                     |
