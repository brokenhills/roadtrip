package com.brokenhills.fileuploadservice.service;

import com.brokenhills.fileuploadservice.model.WorkflowFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static java.lang.String.format;

@Service
public class UploadService {

    private final GridFsTemplate template;
    private final GridFsOperations operations;

    @Autowired
    public UploadService(GridFsTemplate template, GridFsOperations operations) {
        this.template = template;
        this.operations = operations;
    }

    public String addWorkflowFile(String workflowId, MultipartFile file) {
        DBObject meta = new BasicDBObject();
        meta.put("workflowId", workflowId);
        try {
            return template.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), meta).toString();
        } catch (IOException e) {
            throw new RuntimeException(format("File upload error! %s", e.getMessage()), e);
        }
    }

    public List<WorkflowFile> getWorkflowFiles(String workflowId) {
        Iterable<GridFSFile> files = operations.find(new Query(Criteria.where("metadata.workflowId").is(workflowId)));
        List<WorkflowFile> result = new ArrayList<>();
        files.forEach(file -> result.add(WorkflowFile.builder()
                .id(file.getObjectId().toHexString())
                .workflowId(Objects.requireNonNull(file.getMetadata()).get("workflowId").toString())
                .name(file.getFilename())
                .build()));
        return result;
    }

    public WorkflowFile getWorkflowFile(String id) {
        GridFSFile file = Optional.ofNullable(operations.findOne(new Query(Criteria.where("_id").is(id))))
                .orElseThrow(() -> new RuntimeException(format("No such file!, Id : %s", id)));
        return WorkflowFile.builder()
                .workflowId(Objects.requireNonNull(file.getMetadata()).get("workflowId").toString())
                .stream(operations.getResource(file).getContent())
                .build();
    }

    public void deleteFile(String id) {
        template.delete(new Query(Criteria.where("_id").is(id)));
    }
}
