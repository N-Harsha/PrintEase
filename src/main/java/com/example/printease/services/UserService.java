package com.example.printease.services;

import com.example.printease.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserService {
    Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;

}
