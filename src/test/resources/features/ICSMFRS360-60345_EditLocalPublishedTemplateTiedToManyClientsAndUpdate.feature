
Feature: Edit local published template tied to many clients and update
  As a Fi360 Platform user
  I want to edit a published Investment Policy Template
  So that I can verify the delivery time is less than 30 seconds
@ICSMFRS360-60345
  Scenario: Edit and update published template tied to multiple clients
    Given The user is logged into the Cloud Platform application
    Then Launch "Fiduciary Focus Toolkit™"
    Then Click on Settings from top right corner
    Then Navigate to Investment Policy Templates on Settings page
    Then Select the template "QZ Policy Test Template"
    And Update the template with text "This is a test update"
    Then Verify the update is successful