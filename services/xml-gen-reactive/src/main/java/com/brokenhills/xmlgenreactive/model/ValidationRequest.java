package com.brokenhills.xmlgenreactive.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ValidationRequest {

    @NonNull
    private String xmlName;
    @NonNull
    private String xmlBase64;
}
