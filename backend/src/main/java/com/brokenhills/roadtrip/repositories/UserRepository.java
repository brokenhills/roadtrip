package com.brokenhills.roadtrip.repositories;

import com.brokenhills.roadtrip.entities.Department;
import com.brokenhills.roadtrip.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
@CrossOrigin("https://localhost:4200")
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {

    User findByUsername(@Param("username") String username);

    @Query(value = "FROM User du " +
           "JOIN du.department " +
           "JOIN du.role " +
           "LEFT JOIN FETCH du.groups " +
           "WHERE du.username = :username")
    User findByUsernameWithDepartmentAndGroups(String username);

    @Query(value = "FROM User du " +
            "JOIN du.role " +
            "WHERE du.username = :username")
    User findByUsernameWithRoles(String username);

    @Query(value = "FROM User du " +
            "JOIN du.department " +
            "JOIN du.role " +
            "LEFT JOIN FETCH du.groups " +
            "WHERE du.department = :department")
    List<User> findByDepartmentWithGroups(Department department);
}
