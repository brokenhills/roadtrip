package com.brokenhills.xmlgenreactive.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResult {

    private boolean result;
    private String message;
}
