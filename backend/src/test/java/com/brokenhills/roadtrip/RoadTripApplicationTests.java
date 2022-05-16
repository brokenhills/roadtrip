package com.brokenhills.roadtrip;

import com.brokenhills.roadtrip.entities.Department;
import com.brokenhills.roadtrip.entities.User;
import com.brokenhills.roadtrip.entities.UserRole;
import com.brokenhills.roadtrip.entities.Workflow;
import com.brokenhills.roadtrip.models.LoginRequest;
import com.brokenhills.roadtrip.models.LoginResponse;
import com.brokenhills.roadtrip.models.WorkflowRequest;
import com.brokenhills.roadtrip.repositories.DepartmentRepository;
import com.brokenhills.roadtrip.repositories.UserRepository;
import com.brokenhills.roadtrip.repositories.UserRoleRepository;
import com.brokenhills.roadtrip.repositories.WorkflowRepository;
import com.brokenhills.roadtrip.services.CommandLineComponent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.brokenhills.roadtrip.entities.UserRole.RoleType.ADMIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class RoadTripApplicationTests {

    @Container
    private static final PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:latest")
                    .withDatabaseName("integration-test-db")
                    .withUsername("test")
                    .withPassword("test");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private WorkflowRepository workflowRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    CommandLineComponent commandLineComponent;

    private Department dep;
    private User user;
    private String token;

    @DynamicPropertySource
    static void setUpTestProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    public void dbInitOkTest() {
        commandLineComponent
                .run(new DefaultApplicationArguments("init", "--admin=Admin", "--password=test", "--department=Dep"));
        Department dep = departmentRepository.findByName("Dep");
        User admin = userRepository.findByUsername("Admin");
        Assertions.assertEquals("Admin", admin.getUsername());
        Assertions.assertEquals(ADMIN.toString(), admin.getRole().getType());
        Assertions.assertEquals("Dep", dep.getName());
    }

    @Test
    public void createWorkflowOkTest() throws Exception {
        prepareData();
        WorkflowRequest workflowRequest = getWorkflowRequest();
        String workflowRequestString = objectMapper.writeValueAsString(workflowRequest);
        mockMvc.perform(post("/api/workflows")
                        .header("Authorization", String.format("Bearer %s", getJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workflowRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, workflowRepository.findByDepartment(dep).size());
    }

    private void prepareData() throws Exception {
        dep = Department.builder().name("testdep").build();
        UserRole role = UserRole.builder().name("admin").type(ADMIN.toString()).department(dep).build();
        user = User.builder()
                .username("testuser")
                .password(encoder.encode("123456"))
                .isEnabled(true)
                .department(dep)
                .role(role)
                .build();
        departmentRepository.save(dep);
        userRoleRepository.save(role);
        userRepository.save(user);

    }

    private String getJwtToken() throws Exception {
        MvcResult res = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(LoginRequest.builder()
                                .username(user.getUsername())
                                .password("123456")
                                .build())))
                .andExpect(status().isOk()).andReturn();

        return objectMapper.readValue(res.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }

    private WorkflowRequest getWorkflowRequest() {
        return WorkflowRequest.builder()
                .name("testWorkflow")
                .type(Workflow.WorkflowType.TASK.toString())
                .content("testContent")
                .state(Workflow.WorkflowState.NEW.toString())
                .user(String.format("http://localhost:8090/api/users/%s", user.getId()))
                .build();
    }

}
