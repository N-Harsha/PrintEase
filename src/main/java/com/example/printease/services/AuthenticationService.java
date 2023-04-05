package com.example.printease.services;

import com.example.printease.enums.UserRoles;
import com.example.printease.modals.DTO.AuthenticationRequestDTO;
import com.example.printease.modals.DTO.AuthenticationResponseDTO;
import com.example.printease.modals.DTO.RegistrationRequestDTO;
import com.example.printease.modals.DTO.RegistrationResponseDTO;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    Logger logger = Logger.getLogger(AuthenticationService.class.getName());

    private final ServiceProviderService serviceProviderService;
    private final CustomerService customerService;
    private final UserService userService;


    public AuthenticationResponseDTO login(@NonNull AuthenticationRequestDTO authenticationRequestDTO) {
        return AuthenticationResponseDTO.builder().token("token").build();
    }

    @Transactional
    public RegistrationResponseDTO register(@NonNull RegistrationRequestDTO registrationRequestDTO) {

        if (registrationRequestDTO.getUserRole().equals(UserRoles.ROLE_SERVICE_PROVIDER.name())) {
            logger.info("Registration request is for service provider");
            return serviceProviderService.serviceProviderRegistration(registrationRequestDTO);
        }
        else if (registrationRequestDTO.getUserRole().equals(UserRoles.ROLE_CUSTOMER.name())) {
            logger.info("Registration request is for customer");
            return customerService.customerRegistration(registrationRequestDTO);
        }
        else{
            return RegistrationResponseDTO.builder().message("Invalid User Role").build();
        }
    }


}
