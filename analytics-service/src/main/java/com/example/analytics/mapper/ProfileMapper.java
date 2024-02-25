package com.example.analytics.mapper;

import com.example.analytics.entity.Profile;
import com.example.analytics.model.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "budget", source = "budget", numberFormat = "$#.00")
    Profile profileDtoToProfileConvertor(ProfileDto profileDto);

    @Mapping(target = "budget", source = "budget", numberFormat = "$#.00")
    ProfileDto profileToProfileDtoConverter(Profile profile);

}
