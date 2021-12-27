package com.brokenhills.roadtrip.clients;

import com.brokenhills.roadtrip.services.ExternalServicesProps;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class XmlServiceClient {

    private final RestTemplate restTemplate;
    private final ExternalServicesProps servicesProps;

    public XmlServiceClient(RestTemplate restTemplate, ExternalServicesProps servicesProps) {
        this.restTemplate = restTemplate;
        this.servicesProps = servicesProps;
    }

    public String getXmlWithName(String name) {
        return restTemplate.getForObject(
                String.format("%s/private/xml/{id}", servicesProps.getXmlServiceUrl()),
                String.class,
                name);
    }
}
