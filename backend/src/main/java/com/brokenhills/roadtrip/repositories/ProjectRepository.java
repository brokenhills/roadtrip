package com.brokenhills.roadtrip.repositories;

import com.brokenhills.roadtrip.entities.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "projects", path = "projects")
@CrossOrigin("https://localhost:4200")
public interface ProjectRepository extends PagingAndSortingRepository<Project, UUID> {
}
