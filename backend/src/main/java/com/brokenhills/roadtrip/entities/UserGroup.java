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
@Table(name = "groups")
public class UserGroup extends TimestampedModel {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Department.class)
    @JoinColumn(nullable = false)
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    @JsonIgnore
    private Set<User> users;
}
