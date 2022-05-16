package com.brokenhills.roadtrip.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class XmlServiceClient {

    @Value("${services.xmlServiceUrl}")
    private String xmlServiceUrl;

    private final RestTemplate restTemplate;

    public XmlServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getXmlWithName(String name) {
        return restTemplate.getForObject(
                String.format("%s/private/xml/{id}", xmlServiceUrl),
                String.class,
                name);
    }
}
