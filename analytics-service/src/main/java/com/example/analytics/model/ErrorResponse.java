package com.example.analytics.model;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {
    String applicationName;
    String details;
    Instant timestamp;
}
