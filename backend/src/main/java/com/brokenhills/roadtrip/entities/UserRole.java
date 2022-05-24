package com.brokenhills.roadtrip.entities;

import com.brokenhills.roadtrip.annotation.ValueOfEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class UserRole extends TimestampedModel {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @Column(nullable = false)
    @NotEmpty
    @ValueOfEnum(enumClass = RoleType.class)
    private String type;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY,
            targetEntity = Department.class)
    private Department department;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "role",
            targetEntity = User.class)
    @JsonIgnore
    private Set<User> users;

    public enum RoleType {
        ADMIN,
        MANAGER,
        USER;

        public static List<RoleType> USER_ROLES = Arrays.asList(MANAGER, USER);
    }
}
