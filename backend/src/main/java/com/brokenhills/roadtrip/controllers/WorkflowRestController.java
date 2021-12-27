package com.brokenhills.roadtrip.controllers;

import com.brokenhills.roadtrip.entities.Workflow;
import com.brokenhills.roadtrip.models.WorkflowModel;
import com.brokenhills.roadtrip.repositories.WorkflowRepository;
import com.brokenhills.roadtrip.services.WorkflowModelAssembler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RepositoryRestController
public class WorkflowRestController {

    private final WorkflowRepository workflowRepository;
    private final WorkflowModelAssembler workflowModelAssembler;

    public WorkflowRestController(WorkflowRepository workflowRepository, WorkflowModelAssembler workflowModelAssembler) {
        this.workflowRepository = workflowRepository;
        this.workflowModelAssembler = workflowModelAssembler;
    }

    @GetMapping(path = "/workflows/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<WorkflowModel>> getRecentWorkflows() {
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("updated").descending());
        List<Workflow> workflows = workflowRepository.findAll(pageRequest).getContent();
        return new ResponseEntity<>(workflowModelAssembler.toCollectionModel(workflows), HttpStatus.OK);
    }
}

