package com.brokenhills.roadtrip.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "services")
public class ExternalServicesProps {

    private String xmlServiceUrl = "http://localhost:8091";
}
