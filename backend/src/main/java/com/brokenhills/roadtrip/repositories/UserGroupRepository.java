package com.brokenhills.roadtrip.repositories;


import com.brokenhills.roadtrip.entities.UserGroup;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface UserGroupRepository extends PagingAndSortingRepository<UserGroup, UUID> {
}
