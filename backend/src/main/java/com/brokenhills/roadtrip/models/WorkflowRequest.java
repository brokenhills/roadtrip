package com.brokenhills.roadtrip.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowRequest {

    private String name;
    private String type;
    private String state;
    private String content;
    private String child;
    private String parent;
    private String user;
}
