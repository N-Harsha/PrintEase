package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.ServiceProvider;
import com.printease.application.model.User;
import com.printease.application.repository.ServiceProviderRepository;
import com.printease.application.repository.UserRepository;
import com.printease.application.dto.RegistrationResponse;
import com.printease.application.dto.ServiceProviderRegistrationRequest;
import com.printease.application.mapper.ServiceProviderRequestMapper;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.GeneralMessageAccessor;
import com.printease.application.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceOfServiceProvider {
    private final ServiceProviderRepository serviceProviderRepository;
    private final UserRepository userRepository;
    private final ExceptionMessageAccessor exceptionMessageAccessor;

    private final String SERVICE_PROVIDER_NOT_FOUND = "service_provider_not_found";
    private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final GeneralMessageAccessor generalMessageAccessor;

    public RegistrationResponse registration(@Valid ServiceProviderRegistrationRequest registrationRequest) {
        final ServiceProvider serviceProvider = ServiceProviderRequestMapper.INSTANCE.convertToServiceProvider(registrationRequest);
        serviceProvider.setPassword(bCryptPasswordEncoder.encode(serviceProvider.getPassword()));
        serviceProvider.setAccountCreatedOn(LocalDateTime.now());
        serviceProvider.setAccountUpdatedOn(LocalDateTime.now());
        final String email = registrationRequest.getEmail();

        serviceProviderRepository.save(serviceProvider);

        final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, email);

        log.info("{} registered successfully!", email);

        return new RegistrationResponse(registrationSuccessMessage);
    }
    public ServiceProvider findServiceProviderByUserEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null, ProjectConstants.USER_NOT_FOUND, email),
                        HttpStatus.BAD_REQUEST, LocalDateTime.now())));
        return serviceProviderRepository.findById(user.getId()).orElseThrow(() -> new CustomException(new ApiExceptionResponse(
                exceptionMessageAccessor.getMessage(null, SERVICE_PROVIDER_NOT_FOUND, email),
                HttpStatus.BAD_REQUEST, LocalDateTime.now())));
    }

    public ServiceProvider findById(Long serviceProviderId) {
        return serviceProviderRepository.findById(serviceProviderId).orElseThrow(() -> new CustomException(new ApiExceptionResponse(
                exceptionMessageAccessor.getMessage(null, SERVICE_PROVIDER_NOT_FOUND, serviceProviderId),
                HttpStatus.BAD_REQUEST, LocalDateTime.now())));
    }

    public List<ServiceProvider> getAllServiceProviders() {
        return (List<ServiceProvider>) serviceProviderRepository.findAll();
    }
}
