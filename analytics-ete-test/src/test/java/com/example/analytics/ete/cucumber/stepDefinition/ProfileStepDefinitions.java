package com.example.analytics.ete.cucumber.stepDefinition;

import com.example.analytics.ete.cucumber.ScenarioContext;
import com.example.analytics.ete.cucumber.response.ProfileResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;

import static com.example.analytics.ete.cucumber.ScenarioContext.getProfileId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProfileStepDefinitions {

    private final RestTemplate restTemplate = new RestTemplate();
    private String profilePayload;
    private int invalidProfileId;
    @Value("${analytics-service-url}")
    private String baseUrl;

    @Given("I have a profile payload")
    public void iHaveProfilePayload() {
        profilePayload = "{\"name\": \"Mina\", \"surname\": \"Salari\", \"budget\": \"15000\"}";
    }

    @When("I send a POST request to profile\\/create-profile with the payload")
    public void iSendPOSTRequestToCreateProfile() {
        String createProfileUrl = baseUrl + "/profile/create-profile";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity(profilePayload, headers);

        ResponseEntity<Void> response = restTemplate
                .postForEntity(createProfileUrl, requestEntity, Void.class);

        ScenarioContext.setProfileVoidResponseEntity(response);
    }

    @Then("the create response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        ResponseEntity<Void> response = ScenarioContext.getProfileVoidResponseEntity();
        assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

    @Given("I have an existing profile ID and payload")
    public void setExistingProfileIDAndPayload() {
        profilePayload = "{\"name\": \"Mina\", \"surname\": \"Salari\", \"budget\": \"13000\"}";
    }

    @When("I send a PUT request to profile\\/update-profile with the payload and ID")
    public void sendPUTRequestWithPayloadAndID() {
        String updateProfileUrl = baseUrl + "/profile/update-profile";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("profile-Id", String.valueOf(getProfileId()));

        HttpEntity<String> requestEntity = new HttpEntity(profilePayload, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProfileResponse> response = restTemplate.exchange(updateProfileUrl,
                HttpMethod.PUT,
                requestEntity,
                ProfileResponse.class,
                Collections.singletonMap("profileId", getProfileId()));
        ScenarioContext.setProfileDtoResponseEntity(response);
    }

    @Then("the response status code should be {int}")
    public void verifyResponseStatusCode(int expectedStatusCode) {
        ResponseEntity<ProfileResponse> response = ScenarioContext.getProfileDtoResponseEntity();

        assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

    @Given("I have an existing profile ID")
    public void setExistingProfileID() {
    }

    @When("I send a GET request to profile\\/find-profile-by-id with the profile ID")
    public void sendGETRequestWithProfileID() {
        String findProfileUrl = baseUrl + "/profile/find-profile-by-id";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("profile-Id", String.valueOf(getProfileId()));

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ScenarioContext.setProfileDtoResponseEntity(restTemplate.exchange(findProfileUrl,
                HttpMethod.GET,
                requestEntity,
                ProfileResponse.class));
    }

    @Then("the response body should contain profile details")
    public void verifyResponseBodyContainsProfileDetails() {
        ResponseEntity<ProfileResponse> response = ScenarioContext.getProfileDtoResponseEntity();
        ProfileResponse profile = response.getBody();

        assertNotNull(profile);
    }

    @Given("I have an invalid profile ID")
    public void setInvalidProfileID() {
        invalidProfileId = 0;
    }

    @When("I send a GET request to profile\\/find-profile-by-id with the invalid profile ID")
    public void sendGETRequestWithInvalidProfileID() {
        String findProfileUrl = baseUrl + "/profile/find-profile-by-id";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("profile-Id", String.valueOf(invalidProfileId));

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            ScenarioContext.setProfileDtoResponseEntity(restTemplate.exchange(findProfileUrl,
                    HttpMethod.GET,
                    requestEntity,
                    ProfileResponse.class));
        } catch (HttpClientErrorException e) {
            ScenarioContext.setProfileDtoResponseEntity(ResponseEntity
                    .status(e.getStatusCode().value()).build());
        }
    }
}
