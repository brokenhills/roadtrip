package com.brokenhills.roadtrip.repositories;

import com.brokenhills.roadtrip.entities.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends CrudRepository<UserRole, UUID> {

    Optional<UserRole> findByType(String type);
}
