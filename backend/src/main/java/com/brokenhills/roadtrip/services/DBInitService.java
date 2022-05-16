package com.brokenhills.roadtrip.services;

import com.brokenhills.roadtrip.entities.Department;
import com.brokenhills.roadtrip.entities.User;
import com.brokenhills.roadtrip.entities.UserRole;
import com.brokenhills.roadtrip.repositories.DepartmentRepository;
import com.brokenhills.roadtrip.repositories.UserRepository;
import com.brokenhills.roadtrip.repositories.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.brokenhills.roadtrip.entities.UserRole.RoleType.ADMIN;

@Slf4j
@Service
public class DBInitService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder encoder;

    public DBInitService(UserRepository userRepository,
                                UserRoleRepository userRoleRepository,
                                DepartmentRepository departmentRepository,
                                BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = userRoleRepository;
        this.departmentRepository = departmentRepository;
        this.encoder = encoder;
    }

    public void createAdminWithDepartment(String admin, String password, String department) {
        Department dep = Department.builder()
                .name(department)
                .build();
        UserRole role = UserRole.builder()
                .name("Administrator")
                .type(ADMIN.name())
                .department(dep)
                .build();
        User user = User.builder()
                .username(admin)
                .password(encoder.encode(password))
                .isEnabled(true)
                .department(dep)
                .role(role)
                .build();
        departmentRepository.save(dep);
        roleRepository.save(role);
        userRepository.save(user);
        log.info("Admin is {}", user.getUsername());
        log.info("Admin role is: {}", user.getRole().getType());
        log.info("Admin department is {}", user.getDepartment().getName());
    }
}
