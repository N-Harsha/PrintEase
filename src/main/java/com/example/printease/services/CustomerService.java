package com.example.printease.services;

import com.example.printease.enums.UserRoles;
import com.example.printease.modals.Customer;
import com.example.printease.modals.DTO.RegistrationRequestDTO;
import com.example.printease.modals.DTO.RegistrationResponseDTO;
import com.example.printease.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public RegistrationResponseDTO customerRegistration(RegistrationRequestDTO registrationRequestDTO) {
        Customer customer = Customer.builder()
                .name(registrationRequestDTO.getName())
                .email(registrationRequestDTO.getEmail())
//                .favouriteServiceProviders(new ArrayList<>())
                .accountCreatedOn(LocalDateTime.now())
                .accountUpdatedOn(LocalDateTime.now())
                .password(passwordEncoder.encode(registrationRequestDTO.getPassword()))
                .phoneNumber(registrationRequestDTO.getPhoneNumber())
                .userRole(UserRoles.ROLE_CUSTOMER)
                .build();

        customerRepository.save(customer);

        return RegistrationResponseDTO.builder().message("Registration Successful").build();
    }
}
