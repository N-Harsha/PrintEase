package com.example.printease.services;

import com.example.printease.enums.UserRoles;
import com.example.printease.exception.ApiError;
import com.example.printease.modals.DTO.AuthenticationRequestDTO;
import com.example.printease.modals.DTO.AuthenticationResponseDTO;
import com.example.printease.modals.DTO.RegistrationRequestDTO;
import com.example.printease.modals.DTO.RegistrationResponseDTO;
import com.example.printease.security.Jwt.JwtService;
import com.example.printease.security.services.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    Logger logger = Logger.getLogger(AuthenticationService.class.getName());

    private final ServiceProviderService serviceProviderService;
    private final CustomerService customerService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthenticationResponseDTO login(@NonNull AuthenticationRequestDTO authenticationRequestDTO) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequestDTO.getEmail(),
                            authenticationRequestDTO.getPassword()
                    )
            );
        }
        catch (AuthenticationException e){
            logger.info("Authentication failed");
        }
        String jwtToken = jwtService.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .userRole(userDetails.getAuthorities().toString().replace("[", "").replace("]", ""))
                .email(userDetails.getUsername())
                .name(userDetails.getName())
                .build();
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
