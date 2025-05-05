Feature: Boat Fleet Management
  As a member of the yacht club association society
  I want to add, review, update, and remove boats
  So that my club’s fleet information stays accurate

  Scenario: Adding a new boat to the fleet
    Given the member is signed in
    When boat named "Yacht-Inator" with description "Doofenshmirtz's yacht Yacht-Inator" is added
    Then the boat "Yacht-Inator" appears in the fleet list

  Scenario: Viewing the fleet overview
    Given the member is signed in
    And the fleet contains at least one boat
    When they view the fleet overview
    Then they see each boat’s name and description listed

  Scenario: Updating a boat’s details
    Given the boat "Yacht-Inator" exists in the fleet
    And the member is signed in
    When they rename the boat to "Tri-State Tornado" with description "Isabella’s unstoppable craft"
    Then the fleet list shows "Tri-State Tornado" with "Isabella’s unstoppable craft"

  Scenario: Removing a boat from the fleet
    Given the boat "Tri-State Tornado" exists in the fleet
    And the member is signed in
    When they remove the boat from the fleet
    Then "Tri-State Tornado" no longer appears in the fleet list
