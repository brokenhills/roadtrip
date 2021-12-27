package com.brokenhills.roadtrip.repositories;

import com.brokenhills.roadtrip.entities.Department;
import com.brokenhills.roadtrip.entities.User;
import com.brokenhills.roadtrip.entities.Workflow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "workflows", path = "workflows")
@CrossOrigin("https://localhost:4200")
public interface WorkflowRepository extends PagingAndSortingRepository<Workflow, UUID> {

    List<Workflow> findByUser(User user, Pageable pageable);

    @Query(value = "SELECT wf FROM Workflow wf " +
            "JOIN wf.user ur " +
            "WHERE ur.department = :department")
    List<Workflow> findByDepartment(Department department);
}
