package com.brokenhills.roadtrip.controllers;

import com.brokenhills.roadtrip.entities.*;
import com.brokenhills.roadtrip.models.UserForm;
import com.brokenhills.roadtrip.repositories.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.brokenhills.roadtrip.entities.UserRole.RoleType.USER;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final WorkflowRepository workflowRepository;
    private final DepartmentRepository departmentRepository;
    private final UserGroupRepository userGroupRepository;
    private final BCryptPasswordEncoder encoder;

    public AdminController(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           WorkflowRepository workflowRepository,
                           DepartmentRepository departmentRepository,
                           UserGroupRepository userGroupRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.workflowRepository = workflowRepository;
        this.departmentRepository = departmentRepository;
        this.userGroupRepository = userGroupRepository;
        this.encoder = encoder;
    }

    @GetMapping
    public String getAdminPage() {
        return "admin";
    }

    @GetMapping("/users/new")
    public String registrationForm() {
        return "userNew";
    }

    @PostMapping("/users")
    public String registerUser(@AuthenticationPrincipal User admin,  UserForm form) {
        Department department = admin.getDepartment();
        Optional<UserRole> role = userRoleRepository.findByType(USER.name());
        if (!role.isPresent()) {
            role = Optional.of(UserRole.builder().department(department).name("User").type(USER.name()).build());
            userRoleRepository.save(role.get());
        }
        userRepository.save(form.toUser(department, role.get(), encoder));
        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String getUserList(@AuthenticationPrincipal User admin, Model model) {
        Department department = admin.getDepartment();
        List<User> users = userRepository.findByDepartmentWithGroups(department);
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/modify/user/{id}")
    public String getUserForModification(@PathVariable("id") String userId, Model model) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException(String.format("No such user, id: %s", userId)));
        model.addAttribute("user", user);
        return "userModify";
    }

    @PostMapping("/modify/user")
    public String saveModifiedUser(UserForm form) {
        String id = form.getId();
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException(String.format("No such user, id: %s", id)));
        UserRole role = userRoleRepository.findByType(form.getRoleType()).orElse(null);
        userRepository.save(form.toUser(user, role, encoder));
        return "redirect:/admin/users";
    }

    @GetMapping("/workflows")
    public String getDepartmentWorkflows(@AuthenticationPrincipal User admin, Model model) {
        Department department = admin.getDepartment();
        List<Workflow> workflows = workflowRepository.findByDepartment(department);
        model.addAttribute("workflows", workflows);
        return "workflows";
    }

    @GetMapping("/departments/new")
    public String getDepartmentForm() {
        return "departmentNew";
    }

    @GetMapping("/departments")
    public String getAdminDepartments(Model model) {
        Iterable<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @GetMapping("/departments/{id}")
    public String getAdminDepartment(@PathVariable String id, Model model) {
        Department department = departmentRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException(String.format("No such department, id: %s", id)));
        model.addAttribute("department", department);
        return "department";
    }

    @PostMapping("/departments/{id}")
    public String modifyAdminDepartment(@PathVariable String id, Model model) {
        Department department = departmentRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException(String.format("No such department, id: %s", id)));
        return "redirect:/admin/departments";
    }

    @GetMapping("/groups")
    public String getAdminGroups(Model model) {
        Iterable<UserGroup> groups = userGroupRepository.findAll();
        model.addAttribute("groups", groups);
        return "groups";
    }

    @GetMapping("/group/{id}")
    public String getAdminGroup(@PathVariable String id, Model model) {
        UserGroup group = userGroupRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException(String.format("No such group, id: %s", id)));
        model.addAttribute("group", group);
        return "groups";
    }

}
