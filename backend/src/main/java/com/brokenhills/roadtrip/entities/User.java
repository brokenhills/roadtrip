package com.brokenhills.roadtrip.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends TimestampedModel implements UserDetails {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @Column(nullable = false)
    @NotBlank
    private String username;

    @Column(nullable = false)
    @NotBlank
    private String password;

    private String firstName;

    private String middleName;

    private String lastName;

    private boolean isEnabled;

    private boolean isLocked;

    private Instant validUntil;

    @JsonBackReference(value = "department")
    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(nullable = false)
    private Department department;

    @JsonManagedReference(value = "workflowUser")
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            orphanRemoval = true)
    private Set<Workflow> workflows;

    @JsonBackReference(value = "userRole")
    @ManyToOne(targetEntity = UserRole.class)
    @JoinColumn(nullable = false)
    private UserRole role;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = UserGroup.class)
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<UserGroup> groups;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(String.format("ROLE_%s", this.role.getType())));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (this.validUntil == null) {
            return true;
        }
        return Instant.now().isBefore(this.validUntil);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
