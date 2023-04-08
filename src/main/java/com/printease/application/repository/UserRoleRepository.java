package com.printease.application.repository;

import com.printease.application.model.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRoleRepository extends CrudRepository<UserRole,Long> {
    Optional<UserRole> findByRole(String role);

}
