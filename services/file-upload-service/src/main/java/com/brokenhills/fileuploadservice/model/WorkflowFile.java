package com.brokenhills.fileuploadservice.model;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

@Data
@Builder
public class WorkflowFile {

    private String id;
    private String workflowId;
    private String name;
    private InputStream stream;
}
