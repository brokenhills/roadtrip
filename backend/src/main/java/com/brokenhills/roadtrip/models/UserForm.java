package com.brokenhills.roadtrip.models;

import com.brokenhills.roadtrip.entities.Department;
import com.brokenhills.roadtrip.entities.User;
import com.brokenhills.roadtrip.entities.UserRole;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Date;

@Data
public class UserForm {

    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private boolean isEnabled;
    private boolean isLocked;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date validUntil;
    private String department;
    private String roleType;

    public User toUser(Department department,
                       UserRole role,
                       PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .department(department)
                .role(role)
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .build();
    }

    public User toUser(User user,
                       UserRole role,
                       PasswordEncoder passwordEncoder) {
        user.setEnabled(isEnabled);
        user.setLocked(isLocked);
        if (role != null) {
            user.setRole(role);
        }
        user.setValidUntil(validUntil.toInstant());
        if (StringUtils.hasLength(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        user.setUpdated(Instant.now());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setRole(role);
        return user;
    }
}
