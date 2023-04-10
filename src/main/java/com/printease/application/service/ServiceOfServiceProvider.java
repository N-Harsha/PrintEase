package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.ServiceProvider;
import com.printease.application.model.User;
import com.printease.application.repository.ServiceProviderRepository;
import com.printease.application.repository.UserRepository;
import com.printease.application.utils.ExceptionMessageAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ServiceOfServiceProvider {
    private final ServiceProviderRepository serviceProviderRepository;
    private final UserRepository userRepository;
    private final ExceptionMessageAccessor exceptionMessageAccessor;

    private final String SERVICE_PROVIDER_NOT_FOUND = "service_provider_not_found";
    private final String USER_NOT_FOUND = "user_not_found";

    public ServiceProvider findServiceProviderByUserEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null, USER_NOT_FOUND, email),
                        HttpStatus.BAD_REQUEST, LocalDateTime.now())));
        return serviceProviderRepository.findById(user.getId()).orElseThrow(() -> new CustomException(new ApiExceptionResponse(
                exceptionMessageAccessor.getMessage(null, SERVICE_PROVIDER_NOT_FOUND, email),
                HttpStatus.BAD_REQUEST, LocalDateTime.now())));
    }

}
