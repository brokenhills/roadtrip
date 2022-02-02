package com.brokenhills.fileuploadservice.controller;

import com.brokenhills.fileuploadservice.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin(origins = "*")
public class UploadController {

    private final UploadService service;

    @Autowired
    public UploadController(UploadService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("workflowId") String workflowId,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.addWorkflowFile(workflowId, file));
    }

    @GetMapping("/workflow/{id}")
    public ResponseEntity<?> getWorkflowFiles(@PathVariable("id") String workflowId) {
        return ResponseEntity.ok(service.getWorkflowFiles(workflowId));
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<?> getWorkflowFile(@PathVariable("id") String fileId) {
        return ResponseEntity.ok(service.getWorkflowFile(fileId));
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable("id") String fileId) {
        service.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }
}
