package com.brokenhills.xmlgenreactive.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class XsdSchema {

    @Id
    private String xsdName;
    @NonNull
    private String xsdBase64;
}
