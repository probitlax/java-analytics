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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final AnalysisProperties analysisProperties;

    public Profile saveProfile(ProfileDto profileDto) {
        Profile profile = profileMapper.profileDtoToProfileConvertor(profileDto);
        return profileRepository.save(profile);
    }

    public ProfileDto updateProfile(Integer profileId, ProfileDto profileDto) {
        findProfileEntityById(profileId);
        Profile profile = profileMapper.profileDtoToProfileConvertor(profileDto);
        profile.setId(profileId);
        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.profileToProfileDtoConverter(savedProfile);
    }

    public ProfileDto findProfileById(Integer id) {
        Profile profile = findProfileEntityById(id);
        return profileMapper.profileToProfileDtoConverter(profile);
    }

    protected Profile findProfileEntityById(Integer id) {
        return profileRepository.findById(id)
                .orElseThrow(ProfileNotFoundException::new);
    }

    protected void updateProfileBudget(Profile profile, String analysisType){
        BigDecimal analysisPrice = analysisProperties.getPrice().get(AnalysisType.valueOf(analysisType));
        profile.setBudget(profile.getBudget().subtract(analysisPrice));
        profileRepository.save(profile);
    }

    protected void validateProfileBudget(BigDecimal profileBudget, String analysisType) {
        BigDecimal analysisPrice = analysisProperties.getPrice().get(AnalysisType.valueOf(analysisType));
        if (profileBudget.compareTo(analysisPrice) < 0) {
            throw new BudgetNotEnoughException();
        }
    }

}
