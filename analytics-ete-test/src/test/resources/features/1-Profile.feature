Feature: Profile Management

  Scenario: Creating a Profile
    Given I have a profile payload
    When I send a POST request to profile/create-profile with the payload
    Then the create response status code should be 201

  Scenario: Updating a Profile
    Given I have an existing profile ID and payload
    When I send a PUT request to profile/update-profile with the payload and ID
    Then the response status code should be 200

  Scenario: Finding a profile by ID successfully
    Given I have an existing profile ID
    When I send a GET request to profile/find-profile-by-id with the profile ID
    Then the response status code should be 200
    And the response body should contain profile details

  Scenario: Finding a profile with an invalid ID
    Given I have an invalid profile ID
    When I send a GET request to profile/find-profile-by-id with the invalid profile ID
    Then the response status code should be 404


