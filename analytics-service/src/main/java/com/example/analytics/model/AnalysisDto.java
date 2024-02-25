package com.example.analytics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Set;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AnalysisDto {
    private String type;
    private Integer owner;
    private Set<Integer> viewers;
    @JsonInclude(Include. NON_NULL)
    private String hiddenInfo;

    public AnalysisDto(String type, Integer owner, Set<Integer> viewers) {
        this.type = type;
        this.owner = owner;
        this.viewers = viewers;
    }
}
