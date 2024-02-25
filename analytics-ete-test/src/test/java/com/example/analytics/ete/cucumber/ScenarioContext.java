package com.example.analytics.ete.cucumber;

import com.example.analytics.ete.cucumber.response.AnalysisResponse;
import com.example.analytics.ete.cucumber.response.ProfileResponse;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScenarioContext {
    private static ResponseEntity<Void> profileVoidResponseEntity;
    private static ResponseEntity<ProfileResponse> profileDtoResponseEntity;
    private static ResponseEntity<Void> analysisVoidResponseEntity;
    private static ResponseEntity<List<AnalysisResponse>> analysisDtoResponseEntity;
    private static Map<String, Object> scenarioContext = new HashMap<>();

    public static ResponseEntity<Void> getProfileVoidResponseEntity() {
        return profileVoidResponseEntity;
    }

    public static void setProfileVoidResponseEntity(ResponseEntity<Void> profileVoidResponseEntity) {
        ScenarioContext.profileVoidResponseEntity = profileVoidResponseEntity;
        var profileId =  Integer.parseInt(profileVoidResponseEntity.getHeaders().get("Profile-Id").get(0));
        scenarioContext.put("Profile-Id", profileId);
    }

    public static int getProfileId(){
        return (int) scenarioContext.get("Profile-Id");
    }

    public static ResponseEntity<ProfileResponse> getProfileDtoResponseEntity() {
        return profileDtoResponseEntity;
    }

    public static ResponseEntity<Void> getAnalysisVoidResponseEntity() {
        return analysisVoidResponseEntity;
    }

    public static void setAnalysisVoidResponseEntity(ResponseEntity<Void> analysisVoidResponseEntity) {
        ScenarioContext.analysisVoidResponseEntity = analysisVoidResponseEntity;
    }

    public static Map<String, Object> getScenarioContext() {
        return scenarioContext;
    }

    public static void setScenarioContext(Map<String, Object> scenarioContext) {
        ScenarioContext.scenarioContext = scenarioContext;
    }

    public static void setProfileDtoResponseEntity(ResponseEntity<ProfileResponse> profileDtoResponseEntity) {
        ScenarioContext.profileDtoResponseEntity = profileDtoResponseEntity;


    }

    public static ResponseEntity<List<AnalysisResponse>> getAnalysisDtoResponseEntity() {
        return analysisDtoResponseEntity;
    }

    public static void setAnalysisDtoResponseEntity(ResponseEntity<List<AnalysisResponse>> analysisDtoResponseEntity) {
        ScenarioContext.analysisDtoResponseEntity = analysisDtoResponseEntity;
    }


}
