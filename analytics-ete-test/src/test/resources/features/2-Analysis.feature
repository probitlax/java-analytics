Feature: Analysis Service

  Scenario: Creating an analysis
    Given I have an analysis payload
    When I send a POST request to analysis/create-analysis with the analysis payload
    Then the create analysis response status code should be 201

  Scenario: Finding analysis by owner ID
    Given I have a profile ID
    When I send a GET request to analysis/find-analysis-by-owner-id with the profile ID
    Then the get analysis response status code should be 200
    And the response body should contain analysis details with hidden-info

  Scenario: Finding analysis by viewer ID
    Given I have a profile ID
    When I send a GET request to analysis/find-analysis-by-viewer-id with the profile ID
    Then the get analysis by viewer response status code should be 200
    And the response body should contain analysis details without hidden-info