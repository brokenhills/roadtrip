package com.brokenhills.roadtrip.repositories;

import com.brokenhills.roadtrip.entities.Department;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "departments", path = "departments")
@CrossOrigin("https://localhost:4200")
public interface DepartmentRepository extends PagingAndSortingRepository<Department, UUID> {

    Department findByName(@Param("name") String name);

    List<Department> findByNameContainingIgnoreCase(@Param("name") String name);
}
