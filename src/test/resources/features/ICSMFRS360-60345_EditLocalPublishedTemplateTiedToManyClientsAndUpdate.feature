@ICSMFRS360-60345
Feature: Edit local published template tied to many clients and update
  As a Fi360 Platform user
  I want to edit a published Investment Policy Template
  So that I can verify the delivery time is less than 30 seconds

  Background:
    Given The user is logged into the Cloud Platform application
    And The user navigates to Fi360 Administration section
    And The user selects a random workspace
    And The user navigates to Workspace Setup tab
    And The user selects Investment Policy Templates option

  @ICSMFRS360-60345
  Scenario: Verify delivery time when updating published template tied to many clients
    Given at least one published template tied to many clients exists
    When the user expands the options dropdown for a random published template
    Then the dropdown should contain "View Template", "Edit Template", "Archive Template", "Copy Template" options

    When the user selects "View Template" option
    Then the template editor page should be displayed with text editor and sidebar
    And the sidebar should contain "Document", "Questions", "System Field" tabs

    When the user updates text in the text editor
    And the user clicks the "Update" button in the right upper corner
    Then a dialog box should be displayed with Minor/Major Update options

    When the user clicks "Make Update" button
    Then the template should be successfully updated with "Published" status

    When the user navigates to Settings -> Application Management -> Investment Policy Templates
    Then the update made in Workspace Setup should be applied
    And the policy update should take less than 30 seconds