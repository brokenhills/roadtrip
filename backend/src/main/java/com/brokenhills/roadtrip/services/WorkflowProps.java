package com.brokenhills.roadtrip.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Component
@ConfigurationProperties(prefix = "road.workflows")
@Validated
public class WorkflowProps {

    @Min(value = 5)
    @Max(value = 25)
    private int pageSize = 10;
}
