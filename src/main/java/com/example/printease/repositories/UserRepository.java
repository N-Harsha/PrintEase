package com.example.printease.repositories;

import com.example.printease.modals.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findUserByEmail(String email);

}
