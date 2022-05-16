package com.brokenhills.roadtrip.repositories;

import com.brokenhills.roadtrip.entities.Department;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DepartmentRepository extends PagingAndSortingRepository<Department, UUID> {

    Department findByName(@Param("name") String name);
}
