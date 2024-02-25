package com.example.analytics.service;

import com.example.analytics.entity.Analysis;
import com.example.analytics.entity.Profile;
import com.example.analytics.mapper.AnalysisMapper;
import com.example.analytics.model.AnalysisDto;
import com.example.analytics.reposotory.AnalysisRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final ProfileService profileService;
    private final AnalysisMapper analysisMapper;


    @Transactional
    public synchronized void saveAnalysis(AnalysisDto analysisDto) {
        Profile owner = profileService.findProfileEntityById(analysisDto.getOwner());
        profileService.validateProfileBudget(owner.getBudget(), analysisDto.getType());
        Analysis analysis = analysisMapper.analysisDtoToAnalysisConverter(analysisDto);
        analysisRepository.save(analysis);
        profileService.updateProfileBudget(owner, analysisDto.getType());
    }

    public List<AnalysisDto> findAnalysisByOwnerId(Integer profileId) {
        List<Analysis> analysisList = analysisRepository.findByOwner_IdOrderByIdDesc(profileId);
        return analysisMapper.analysisSetToAnalysisDtoConverter(analysisList);
    }

    public List<AnalysisDto> findAnalysisByViewerId(Integer profileId) {
        List<Analysis> analysisList = analysisRepository.findByViewerOrderByIdDesc(profileId);
        return analysisMapper.analysisSetToAnalysisDtoConverterWithoutHiddenInfo(analysisList);
    }

}
