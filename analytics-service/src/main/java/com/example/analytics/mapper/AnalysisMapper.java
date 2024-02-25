package com.example.analytics.mapper;

import com.example.analytics.entity.Analysis;
import com.example.analytics.entity.Profile;
import com.example.analytics.model.AnalysisDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnalysisMapper {


    @Mapping(target = "owner.id", source = "owner")
    @Mapping(target = "analysisType", source = "type")
    Analysis analysisDtoToAnalysisConverter(AnalysisDto analysisDto);

    default Integer fromViewerToInteger(Profile viewer){
        return viewer == null ? null : viewer.getId();
    }

    default Profile fromIntegerToViewer(Integer id){
        return new Profile(id);
    }

    @Mapping(target = "owner", source = "owner.id")
    @Mapping(target = "type", source = "analysisType")
    AnalysisDto analysisToAnalysisDtoConverter(Analysis analysis);


    List<AnalysisDto> analysisSetToAnalysisDtoConverter(List<Analysis> analysis);

    default List<AnalysisDto> analysisSetToAnalysisDtoConverterWithoutHiddenInfo(List<Analysis> analysis){
        List<AnalysisDto> analysisDtos = new ArrayList<>();
        for(Analysis a: analysis){
            Set<Profile> viewers = a.getViewers();
            Set<Integer> viewersId = new HashSet<>();
            for(Profile v: viewers){
                viewersId.add(v.getId());
            }
            AnalysisDto analysisDto = new AnalysisDto(a.getAnalysisType().name(), a.getOwner().getId(), viewersId);
            analysisDtos.add(analysisDto);
        }
        return analysisDtos;
    }
}
