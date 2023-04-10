package com.printease.application.repository;

import com.printease.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	boolean existsByEmail(String email);


    Optional<User> findUserByEmail(String email);
}
