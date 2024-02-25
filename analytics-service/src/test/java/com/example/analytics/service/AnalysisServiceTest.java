package com.example.analytics.service;

import com.example.analytics.entity.Analysis;
import com.example.analytics.entity.AnalysisType;
import com.example.analytics.entity.Profile;
import com.example.analytics.exception.BudgetNotEnoughException;
import com.example.analytics.mapper.AnalysisMapper;
import com.example.analytics.model.AnalysisDto;
import com.example.analytics.reposotory.AnalysisRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
class AnalysisServiceTest {

    static final BigDecimal ENOUGH_BUDGET = new BigDecimal("15000.00");
    static final BigDecimal NOT_ENOUGH_BUDGET = new BigDecimal("150.00");


    @Mock
    private AnalysisRepository analysisRepository;

    @Mock
    private ProfileService profileService;

    @Mock
    private AnalysisMapper analysisMapper;

    @InjectMocks
    private AnalysisService analysisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAnalysisWithoutViewer_ifEnoughBudget_saveSuccessfully() {
        AnalysisDto analysisDto = new AnalysisDto("FIRST",1, null);
        Profile overBudgetProfile = new Profile(1, "name", "surname", ENOUGH_BUDGET);
        Analysis analysis = new Analysis(null, AnalysisType.FIRST, overBudgetProfile, new HashSet<>());

        when(profileService.findProfileEntityById(anyInt())).thenReturn(overBudgetProfile);
        doNothing().when(profileService).validateProfileBudget(overBudgetProfile.getBudget(), analysisDto.getType());
        when(analysisMapper.analysisDtoToAnalysisConverter(any(AnalysisDto.class))).thenReturn(analysis);
        when(analysisRepository.save(any(Analysis.class))).thenReturn(analysis);

        analysisService.saveAnalysis(analysisDto);

        verify(profileService, times(1)).findProfileEntityById(anyInt());
        verify(profileService, times(1)).validateProfileBudget(any(), any());
        verify(analysisMapper, times(1)).analysisDtoToAnalysisConverter(any(AnalysisDto.class));
        verify(analysisRepository, times(1)).save(any(Analysis.class));
        verify(profileService, times(1)).updateProfileBudget(any(Profile.class), any());
    }

    @Test
    void testSaveAnalysisWithViewer_ifEnoughBudget_saveSuccessfully() {
        Set<Integer> viewerIds = new HashSet<>();
        viewerIds.add(1);
        AnalysisDto analysisDto = new AnalysisDto("FIRST",1, viewerIds);
        Profile overBudgetProfile = new Profile(1, "name", "surname", ENOUGH_BUDGET);
        Set<Profile> viewers = new HashSet<>();
        viewers.add(overBudgetProfile);
        Analysis analysis = new Analysis(null, AnalysisType.FIRST, overBudgetProfile, viewers);

        when(profileService.findProfileEntityById(anyInt())).thenReturn(overBudgetProfile);
        doNothing().when(profileService).validateProfileBudget(overBudgetProfile.getBudget(), analysisDto.getType());
        when(analysisMapper.analysisDtoToAnalysisConverter(any(AnalysisDto.class))).thenReturn(analysis);
        when(analysisRepository.save(any(Analysis.class))).thenReturn(analysis);

        analysisService.saveAnalysis(analysisDto);

        verify(profileService, times(1)).findProfileEntityById(anyInt());
        verify(profileService, times(1)).validateProfileBudget(any(), any());
        verify(analysisMapper, times(1)).analysisDtoToAnalysisConverter(any(AnalysisDto.class));
        verify(analysisRepository, times(1)).save(any(Analysis.class));
        verify(profileService, times(1)).updateProfileBudget(any(Profile.class), any());
    }

    @Test
    void testSaveAnalysisWithViewer_notEnoughBudget_BudgetNotEnoughException() {
        Set<Integer> viewerIds = new HashSet<>();
        viewerIds.add(1);
        AnalysisDto analysisDto = new AnalysisDto("FIRST",1, viewerIds);
        Profile underBudgetProfile = new Profile(1, "name", "surname", NOT_ENOUGH_BUDGET);

        when(profileService.findProfileEntityById(anyInt())).thenReturn(underBudgetProfile);
        doThrow(BudgetNotEnoughException.class).when(profileService).validateProfileBudget(underBudgetProfile.getBudget(), analysisDto.getType());

        assertThrows(BudgetNotEnoughException.class, () -> analysisService.saveAnalysis(analysisDto));
    }



    @Test
    void testFindAnalysisByOwnerId() {
        Integer profileId = 1;
        Profile overBudgetProfile = new Profile(profileId, "name", "surname", ENOUGH_BUDGET);
        Set<Profile> viewers = new HashSet<>();
        viewers.add(overBudgetProfile);
        Analysis analysis = new Analysis(null, AnalysisType.FIRST, overBudgetProfile, viewers);
        List<Analysis> analysisList = new ArrayList<>();
        analysisList.add(analysis);
        Set<Integer> viewerIds = new HashSet<>();
        viewerIds.add(1);

        AnalysisDto analysisDto = new AnalysisDto("FIRST",profileId, viewerIds);
        List<AnalysisDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(analysisDto);

        when(analysisRepository.findByOwner_IdOrderByIdDesc(profileId)).thenReturn(analysisList);
        when(analysisMapper.analysisSetToAnalysisDtoConverter(analysisList)).thenReturn(expectedDtoList);

        List<AnalysisDto> result = analysisService.findAnalysisByOwnerId(profileId);

        verify(analysisRepository, times(1)).findByOwner_IdOrderByIdDesc(profileId);
        verify(analysisMapper, times(1)).analysisSetToAnalysisDtoConverter(analysisList);

        assertEquals(result, expectedDtoList);
    }

    @Test
    void testFindAnalysisByViewerId() {
        Integer profileId = 1;
        Profile overBudgetProfile = new Profile(profileId, "name", "surname", ENOUGH_BUDGET);
        Set<Profile> viewers = new HashSet<>();
        viewers.add(overBudgetProfile);
        Analysis analysis = new Analysis(null, AnalysisType.FIRST, overBudgetProfile, viewers);
        List<Analysis> analysisList = new ArrayList<>();
        analysisList.add(analysis);
        Set<Integer> viewerIds = new HashSet<>();
        viewerIds.add(1);

        AnalysisDto analysisDto = new AnalysisDto("FIRST",profileId, viewerIds);
        List<AnalysisDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(analysisDto);

        when(analysisRepository.findByViewerOrderByIdDesc(profileId)).thenReturn(analysisList);
        when(analysisMapper.analysisSetToAnalysisDtoConverterWithoutHiddenInfo(analysisList)).thenReturn(expectedDtoList);

        List<AnalysisDto> result = analysisService.findAnalysisByViewerId(profileId);

        verify(analysisRepository, times(1)).findByViewerOrderByIdDesc(profileId);
        verify(analysisMapper, times(1)).analysisSetToAnalysisDtoConverterWithoutHiddenInfo(analysisList);

        assertEquals(result, expectedDtoList);
    }
/*
    @Test
    void testFindByOwnerOrViewerId() {
        Integer profileId = 1;
        Profile overBudgetProfile = new Profile(profileId, "name", "surname", ENOUGH_BUDGET);
        Set<Profile> viewers = new HashSet<>();
        viewers.add(overBudgetProfile);
        Analysis analysis = new Analysis(null, AnalysisType.FIRST, overBudgetProfile, viewers);
        List<Analysis> analysisList = new ArrayList<>();
        analysisList.add(analysis);
        Set<Integer> viewerIds = new HashSet<>();
        viewerIds.add(profileId);

        AnalysisDto analysisDto = new AnalysisDto("FIRST",profileId, viewerIds);
        List<AnalysisDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(analysisDto);

        Set<AnalysisDto> finalExpectedDtoList = new HashSet<>();
        finalExpectedDtoList.add(analysisDto);
        finalExpectedDtoList.add(analysisDto);

        when(analysisRepository.findByOwner_IdOrderByIdDesc(profileId)).thenReturn(analysisList);
        when(analysisMapper.analysisSetToAnalysisDtoConverter(analysisList)).thenReturn(expectedDtoList);

        when(analysisRepository.findByViewerOrderByIdDesc(profileId)).thenReturn(analysisList);
        when(analysisMapper.analysisSetToAnalysisDtoConverterWithoutHiddenInfo(analysisList)).thenReturn(expectedDtoList);

        Set<AnalysisDto> result = analysisService.findByOwnerOrViewerId(profileId);

        verify(analysisRepository, times(1)).findByViewerOrderByIdDesc(profileId);
        verify(analysisMapper, times(1)).analysisSetToAnalysisDtoConverterWithoutHiddenInfo(analysisList);
        verify(analysisRepository, times(1)).findByOwner_IdOrderByIdDesc(profileId);
        verify(analysisMapper, times(1)).analysisSetToAnalysisDtoConverter(analysisList);

        assertEquals(finalExpectedDtoList, result);
    }*/

}
