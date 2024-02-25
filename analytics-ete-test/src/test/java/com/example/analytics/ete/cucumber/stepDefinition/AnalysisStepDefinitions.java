package com.example.analytics.ete.cucumber.stepDefinition;

import com.example.analytics.ete.cucumber.ScenarioContext;
import com.example.analytics.ete.cucumber.response.AnalysisResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.analytics.ete.cucumber.ScenarioContext.getProfileId;
import static org.junit.jupiter.api.Assertions.*;

public class AnalysisStepDefinitions {

    private final RestTemplate restTemplate = new RestTemplate();
    private String analysisPayload;
    @Value("${analytics-service-url}")
    private String baseUrl;


    @Given("I have an analysis payload")
    public void setAnalysisPayload() {
        int profileId = getProfileId();
        analysisPayload = "{\n" +
                "  \"type\": \"SECOND\",\n" +
                "  \"owner\": " + profileId + ",\n" +
                "  \"viewers\": [" + profileId + "],\n" +
                "  \"hiddenInfo\": \"test1 hidden-info\"\n" +
                "}";
    }

    @When("I send a POST request to analysis\\/create-analysis with the analysis payload")
    public void sendPOSTRequestWithAnalysisPayload() {
        String createAnalysisUrl = baseUrl + "/analysis/create-analysis";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(analysisPayload, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(createAnalysisUrl
                , requestEntity
                , Void.class);
        ScenarioContext.setAnalysisVoidResponseEntity(response);
    }

    @Then("the create analysis response status code should be {int}")
    public void verifyCreateResponseStatusCode(int expectedStatusCode) {
        ResponseEntity<Void> response = ScenarioContext.getAnalysisVoidResponseEntity();

        assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

    @Given("I have a profile ID")
    public void setProfileID() {
    }

    @When("I send a GET request to analysis\\/find-analysis-by-owner-id with the profile ID")
    public void sendGETRequestToFindAnalysisByOwnerID() {
        String findAnalysisByOwnerUrl = baseUrl + "/analysis/find-analysis-by-owner-id";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("profile-Id", String.valueOf(getProfileId()));

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<AnalysisResponse>> response = restTemplate.exchange(findAnalysisByOwnerUrl,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        ScenarioContext.setAnalysisDtoResponseEntity(response);
    }

    @Then("the get analysis response status code should be {int}")
    public void verifyGetResponseStatusCode(int expectedStatusCode) {
        ResponseEntity<List<AnalysisResponse>> response = ScenarioContext.getAnalysisDtoResponseEntity();

        assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

    @Then("the response body should contain analysis details with hidden-info")
    public void verifyResponseBodyContainsAnalysisWithHiddenInfo() {
        List<AnalysisResponse> analysisList = ScenarioContext.getAnalysisDtoResponseEntity().getBody();
        assertTrue(analysisList != null && !analysisList.isEmpty());

        for (AnalysisResponse analysis : analysisList) {
            assertEquals(getProfileId(), analysis.getOwner());
            assertNotNull(analysis.getHiddenInfo());
        }
    }

    @When("I send a GET request to analysis\\/find-analysis-by-viewer-id with the profile ID")
    public void sendGETRequestToFindAnalysisByViewerID() {
        String findAnalysisByViewerUrl = baseUrl + "/analysis/find-analysis-by-viewer-id";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("profile-Id", String.valueOf(getProfileId()));

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<AnalysisResponse>> response = restTemplate.exchange(findAnalysisByViewerUrl,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        ScenarioContext.setAnalysisDtoResponseEntity(response);
    }

    @Then("the get analysis by viewer response status code should be {int}")
    public void verifyGetByViewerResponseStatusCode(int expectedStatusCode) {
        ResponseEntity<List<AnalysisResponse>> response = ScenarioContext.getAnalysisDtoResponseEntity();

        assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

    @Then("the response body should contain analysis details without hidden-info")
    public void verifyResponseBodyContainsAnalysisWithoutHiddenInfo() {
        List<AnalysisResponse> analysisList = ScenarioContext.getAnalysisDtoResponseEntity().getBody();
        assertTrue(analysisList != null && !analysisList.isEmpty());

        for (AnalysisResponse analysis : analysisList) {
            assert (analysis.getViewers().contains(getProfileId()));
            assertNull(analysis.getHiddenInfo());
        }
    }

}
