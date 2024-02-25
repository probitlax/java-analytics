package com.example.analytics.service;

import com.example.analytics.entity.AnalysisType;
import com.example.analytics.entity.Profile;
import com.example.analytics.config.AnalysisProperties;
import com.example.analytics.exception.BudgetNotEnoughException;
import com.example.analytics.exception.ProfileNotFoundException;
import com.example.analytics.mapper.ProfileMapper;
import com.example.analytics.model.ProfileDto;
import com.example.analytics.reposotory.ProfileRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.example.analytics.service.AnalysisServiceTest.ENOUGH_BUDGET;
import static com.example.analytics.service.AnalysisServiceTest.NOT_ENOUGH_BUDGET;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Mock
    private AnalysisProperties analysisProperties;

    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Map<AnalysisType, BigDecimal> analysisPrices = new HashMap<>();
        analysisPrices.put(AnalysisType.FIRST, new BigDecimal("10.00"));
        analysisPrices.put(AnalysisType.SECOND, new BigDecimal("20.00"));
        when(analysisProperties.getPrice()).thenReturn(analysisPrices);
    }

    @Test
    void testSaveProfile() {
        ProfileDto profileDto = new ProfileDto("name", "surname", ENOUGH_BUDGET);
        Profile mappedProfile = new Profile(null, "name", "surname", ENOUGH_BUDGET);

        when(profileMapper.profileDtoToProfileConvertor(profileDto)).thenReturn(mappedProfile);
        when(profileRepository.save(mappedProfile)).thenReturn(mappedProfile);

        Profile result = profileService.saveProfile(profileDto);

        verify(profileMapper, times(1)).profileDtoToProfileConvertor(profileDto);
        verify(profileRepository, times(1)).save(mappedProfile);

        assertEquals(mappedProfile, result);
    }

    @Test
    void testUpdateProfile() {
        Integer profileId = 1;
        ProfileDto profileDto = new ProfileDto("name", "surname", NOT_ENOUGH_BUDGET);
        Profile oldProfile = new Profile(profileId, "name", "surname", NOT_ENOUGH_BUDGET);
        Profile updatedProfile = new Profile(profileId, "name", "surname", ENOUGH_BUDGET);
        ProfileDto UpdatedProfileDto = new ProfileDto("name", "surname", ENOUGH_BUDGET);

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(oldProfile));
        when(profileMapper.profileDtoToProfileConvertor(profileDto)).thenReturn(updatedProfile);
        when(profileRepository.save(updatedProfile)).thenReturn(updatedProfile);
        when(profileMapper.profileToProfileDtoConverter(updatedProfile)).thenReturn(UpdatedProfileDto);

        ProfileDto result = profileService.updateProfile(profileId, profileDto);

        verify(profileMapper, times(1)).profileDtoToProfileConvertor(profileDto);
        verify(profileRepository, times(1)).save(updatedProfile);
        verify(profileMapper, times(1)).profileToProfileDtoConverter(updatedProfile);

        assertEquals(UpdatedProfileDto, result);
    }

    @Test
    void testFindProfileById_Exists() {
        Integer profileId = 1;
        ProfileDto profileDto = new ProfileDto("name", "surname", ENOUGH_BUDGET);
        Profile profile = new Profile(profileId, "name", "surname", ENOUGH_BUDGET);

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
        when(profileMapper.profileToProfileDtoConverter(profile)).thenReturn(profileDto);

        ProfileDto result = profileService.findProfileById(profileId);

        verify(profileRepository, times(1)).findById(profileId);
        verify(profileMapper, times(1)).profileToProfileDtoConverter(profile);

        assertEquals(profileDto, result);
    }

    @Test
    void testFindProfileById_NotExists() {
        Integer profileId = 1;

        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        assertThrows(ProfileNotFoundException.class, () -> profileService.findProfileById(profileId));
    }

    @Test
    void testFindProfileEntityById_Exists() {
        Integer profileId = 1;
        Profile profile = new Profile(profileId, "name", "surname", ENOUGH_BUDGET);

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        Profile result = profileService.findProfileEntityById(profileId);

        verify(profileRepository, times(1)).findById(profileId);

        assertEquals(profile, result);
    }

    @Test
    void testFindProfileEntityById_NotExists() {
        Integer profileId = 1;

        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        assertThrows(ProfileNotFoundException.class, () -> profileService.findProfileById(profileId));
    }

    @Test
    void testUpdateProfileBudget() {
        Map<AnalysisType, BigDecimal> analysisPrices = new HashMap<>();
        analysisPrices.put(AnalysisType.FIRST, new BigDecimal("10.00"));
        analysisPrices.put(AnalysisType.SECOND, new BigDecimal("20.00"));
        when(analysisProperties.getPrice()).thenReturn(analysisPrices);

        Profile profile = new Profile(1, "name", "surname", ENOUGH_BUDGET);

        profileService.updateProfileBudget(profile, AnalysisType.FIRST.name());

        verify(profileRepository, times(1)).save(profile);

        BigDecimal expectedBudget = ENOUGH_BUDGET.subtract(new BigDecimal(10.00));
        assertEquals(expectedBudget, profile.getBudget());

    }

    @Test
    void testValidateProfileBudget_EnoughBudget() {
        BigDecimal profileBudget = new BigDecimal("15.00");

        profileService.validateProfileBudget(profileBudget, AnalysisType.FIRST.name());
    }

    @Test
    void testValidateProfileBudget_NotEnoughBudget() {
        BigDecimal profileBudget = new BigDecimal("5.00");
        Executable executable = () -> profileService.validateProfileBudget(profileBudget, AnalysisType.FIRST.name());

        assertThrows(BudgetNotEnoughException.class, executable);
    }
}
