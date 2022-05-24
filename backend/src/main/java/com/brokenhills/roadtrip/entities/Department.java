package com.brokenhills.roadtrip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "departments")
public class Department extends TimestampedModel {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "department",
            targetEntity = User.class,
            orphanRemoval = true)
    @JsonIgnore
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "department",
            targetEntity = UserGroup.class,
            orphanRemoval = true)
    @JsonIgnore
    private Set<UserGroup> groups;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "department",
            targetEntity = Project.class,
            orphanRemoval = true)
    @JsonIgnore
    private Set<Project> projects;
}
