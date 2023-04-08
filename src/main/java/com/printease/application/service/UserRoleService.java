package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.UserRole;
import com.printease.application.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    public UserRole findByRole(String role){
        return userRoleRepository.findByRole(role).orElseThrow(()->new CustomException(new ApiExceptionResponse("Role not found", HttpStatus.BAD_REQUEST, LocalDateTime.now())));
    }

}
