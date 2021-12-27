package com.brokenhills.roadtrip.repositories;

import com.brokenhills.roadtrip.entities.Department;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface DepartmentRepository extends PagingAndSortingRepository<Department, UUID> {
}
