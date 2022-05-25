package com.brokenhills.roadtrip.controllers;

import com.brokenhills.roadtrip.entities.Workflow;
import com.brokenhills.roadtrip.repositories.WorkflowRepository;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/board")
@CrossOrigin(origins = "*")
public class WorkBoardRestController {

    private final RepositoryEntityLinks links;
    private final WorkflowRepository workflowRepository;

    public WorkBoardRestController(RepositoryEntityLinks links,
                                   WorkflowRepository workflowRepository) {
        this.links = links;
        this.workflowRepository = workflowRepository;
    }

    @GetMapping(path = "/{userId}", produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Map<String, List<EntityModel<Workflow>>>> getRecentWorkflows(@PathVariable("userId") String userId) {
        Link link = linkTo(methodOn(WorkBoardRestController.class).getRecentWorkflows(userId)).withSelfRel();
        Map<String, List<EntityModel<Workflow>>> map = workflowRepository.findByUserId(UUID.fromString(userId))
                .stream()
                .collect(groupingBy(Workflow::getState,
                        mapping(l -> toEntityModelWithLinks(l, l.getId()), toList())));
        return EntityModel.of(map).add(link);
    }

    private <T> EntityModel<T> toEntityModelWithLinks(T entity, Object entityId) {
        return EntityModel
                .of(entity)
                .add(links.linkToItemResource(WorkflowRepository.class, entityId)
                .withSelfRel());
    }
}
