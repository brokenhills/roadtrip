package com.brokenhills.roadtrip.entities;

import com.brokenhills.roadtrip.annotation.ValueOfEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "workflows")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Workflow extends TimestampedModel {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotEmpty
    @ValueOfEnum(enumClass = WorkflowType.class)
    private String type;

    private String content;

    @Column(nullable = false)
    @NotEmpty
    @ValueOfEnum(enumClass = WorkflowState.class)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    private Workflow parent;

    @Type(type = "list-array")
    @Column(columnDefinition = "uuid[]")
    private List<UUID> child;

    @JsonBackReference(value = "workflowUsers")
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(nullable = false)
    private User user;

    @JsonBackReference(value = "project")
    @ManyToOne(targetEntity = Project.class)
    private Project project;

    public enum WorkflowType {
        TASK,
        REQUIREMENT,
        ISSUE,
        BUG;
    }

    public enum WorkflowState {
        NEW,
        ACTIVE,
        CLOSED;
    }
}
