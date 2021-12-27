package com.brokenhills.roadtrip.services;

import com.brokenhills.roadtrip.controllers.WorkflowRestController;
import com.brokenhills.roadtrip.entities.Workflow;
import com.brokenhills.roadtrip.models.WorkflowModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class WorkflowModelAssembler extends RepresentationModelAssemblerSupport<Workflow, WorkflowModel> {

    public WorkflowModelAssembler() {
        super(WorkflowRestController.class, WorkflowModel.class);
    }

    @Override
    public WorkflowModel toModel(Workflow entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    public CollectionModel<WorkflowModel> toCollectionModel(Iterable<? extends Workflow> entities) {
        CollectionModel<WorkflowModel> workflowModels = super.toCollectionModel(entities);
        workflowModels.add(linkTo(methodOn(WorkflowRestController.class).getRecentWorkflows()).withSelfRel());
        return workflowModels;
    }

    @Override
    protected WorkflowModel instantiateModel(Workflow entity) {
        return new WorkflowModel(entity);
    }
}
