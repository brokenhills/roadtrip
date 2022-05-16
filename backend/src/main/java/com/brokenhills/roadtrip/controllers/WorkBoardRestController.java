package com.brokenhills.roadtrip.controllers;

import com.brokenhills.roadtrip.entities.Workflow;
import com.brokenhills.roadtrip.repositories.WorkflowRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RestController
@RequestMapping(path = "/api/board")
@CrossOrigin(origins = "*")
public class WorkBoardRestController {

    private final WorkflowRepository workflowRepository;

    public WorkBoardRestController(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<Workflow>> getRecentWorkflows() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("updated").descending());
        List<Workflow> workflows = workflowRepository.findAll(pageRequest).getContent();
        return workflows.stream().collect(groupingBy(Workflow::getState));
    }
}
