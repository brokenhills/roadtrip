package com.brokenhills.roadtrip.models;


import com.brokenhills.roadtrip.entities.Project;
import com.brokenhills.roadtrip.entities.User;
import com.brokenhills.roadtrip.entities.Workflow;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Relation(value = "workflow", collectionRelation = "workflows")
public class WorkflowModel extends RepresentationModel<WorkflowModel> {

    @Getter
    private final String name;
    @Getter
    private final Instant created;
    @Getter
    private final Instant updated;
    @Getter
    private final String type;
    @Getter
    private final String content;
    @Getter
    private final String state;
    @Getter
    private final Workflow parent;
    @Getter
    private final List<UUID> child;
    @Getter
    private final User user;
    @Getter
    private final Project project;

    public WorkflowModel(Workflow workflow) {
        this.name = workflow.getName();
        this.created = workflow.getCreated();
        this.updated = workflow.getUpdated();
        this.type = workflow.getType();
        this.content = workflow.getContent();
        this.state = workflow.getState();
        this.parent = workflow.getParent();
        this.child = workflow.getChild();
        this.user = workflow.getUser();
        this.project = workflow.getProject();
    }
}
