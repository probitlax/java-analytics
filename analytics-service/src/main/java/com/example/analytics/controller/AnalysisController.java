package com.example.analytics.controller;


import com.example.analytics.model.AnalysisDto;
import com.example.analytics.service.AnalysisService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${spring.webservices.path}/analysis")
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/create-analysis")
    public ResponseEntity<Void> saveAnalysis(@RequestBody AnalysisDto analysisDto) {
        analysisService.saveAnalysis(analysisDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/find-analysis-by-owner-id/{profileId}")
    public ResponseEntity<List<AnalysisDto>> findAnalysisByOwnerId(@PathVariable("profileId") Integer profileId){
        return ResponseEntity.ok(analysisService.findAnalysisByOwnerId(profileId));
    }

    @GetMapping("/find-analysis-by-viewer-id/{profileId}")
    public ResponseEntity<List<AnalysisDto>> findAnalysisByViewerId(@PathVariable("profileId") Integer profileId){
        return ResponseEntity.ok(analysisService.findAnalysisByViewerId(profileId));
    }

}
