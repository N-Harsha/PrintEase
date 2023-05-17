package com.printease.application.security.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.mapper.ServiceProviderRequestMapper;
import com.printease.application.security.utils.Constants;
import com.printease.application.service.CustomerService;
import com.printease.application.service.ServiceOfServiceProvider;
import com.printease.application.service.UserRoleService;
import com.printease.application.service.UserValidationService;
import com.printease.application.model.User;
import com.printease.application.dto.AuthenticatedUserDto;
import com.printease.application.dto.RegistrationRequest;
import com.printease.application.dto.RegistrationResponse;
import com.printease.application.mapper.UserMapper;
import com.printease.application.repository.UserRepository;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final ServiceOfServiceProvider serviceOfServiceProvider;

    private final CustomerService customerService;

    private final UserValidationService userValidationService;

    private final UserRoleService userRoleService;

    private final ExceptionMessageAccessor exceptionMessageAccessor;


    public User findByUsername(String email) {

        return userRepository.findByEmail(email);
    }

    public RegistrationResponse registration(RegistrationRequest registrationRequest) {

        userValidationService.validateUser(registrationRequest);

        if (registrationRequest.getUserRole().getRole().equalsIgnoreCase("customer")) {
            registrationRequest.setUserRole(userRoleService.findByRole(Constants.ROLE_CUSTOMER));
            return customerService.registration(registrationRequest);
        } else {
            registrationRequest.setUserRole(userRoleService.findByRole(Constants.ROLE_SERVICE_PROVIDER));
            return serviceOfServiceProvider.registration(ServiceProviderRequestMapper.INSTANCE.convertToServiceProviderRegistrationRequest(registrationRequest));
        }

    }

    public AuthenticatedUserDto findAuthenticatedUserByUsername(String email) {

        final User user = findByUsername(email);

        return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new CustomException(new ApiExceptionResponse(
                exceptionMessageAccessor.getMessage(null, ProjectConstants.USER_NOT_FOUND), HttpStatus.NOT_FOUND, LocalDateTime.now())));
    }
}
