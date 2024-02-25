package com.example.analytics.config;

import com.example.analytics.entity.AnalysisType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "analysis")
@Setter
@Getter
public class AnalysisProperties {
    private Map<AnalysisType, BigDecimal> price;
}
