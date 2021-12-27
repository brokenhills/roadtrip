package com.brokenhills.xmlgenreactive.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenerationResult {

    private String xmlBase64;
    private String message;
}
