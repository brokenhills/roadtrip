package com.brokenhills.roadtrip.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "projects")
public class Project extends TimestampedModel {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @Column(name = "date_from")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateFrom;

    @Column(name = "date_to")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTo;

    @ManyToOne(targetEntity = Department.class)
    private Department department;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "project",
            orphanRemoval = true)
    private Set<Workflow> workflows;
}
