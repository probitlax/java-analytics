package com.example.analytics.ete.cucumber.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@TestPropertySource(locations = "classpath:application-test.yml")
public class CucumberSpringContextConfiguration {
}
