package com.brokenhills.xmlgenreactive.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class XmlGenParams {

    private String schemaName;
    private String workflowId;
    private String workflowName;
    private String workflowType;
    private String workflowState;
    private String workflowCreated;
    private String userId;
    private String userUsername;
    private String userFirstName;
    private String userLastName;
    private String userMiddleName;
    private String departmentId;
    private String departmentName;
    private String parentId;
    private List<String> childIds;
    private String content;
}
