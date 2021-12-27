package com.brokenhills.roadtrip.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "department",
            targetEntity = User.class,
            orphanRemoval = true)
    private Set<User> users;
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "department",
            targetEntity = UserGroup.class,
            orphanRemoval = true)
    private Set<UserGroup> groups;

}
