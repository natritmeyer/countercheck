Feature: Owner management
  As a pet owner
  In order to keep my pet healthy
  I want to be able to register it with a vet

  Scenario: Add a new owner
    Given I am not a registered owner
    When I register myself as an owner
    Then I am registered as an owner
