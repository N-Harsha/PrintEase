package com.printease.application.security.service;

import com.printease.application.model.Customer;
import com.printease.application.model.ServiceProvider;
import com.printease.application.model.UserRole;
import com.printease.application.repository.ServiceProviderRepository;
import com.printease.application.security.dto.RegistrationResponse;
import com.printease.application.security.dto.ServiceProviderRegistrationRequest;
import com.printease.application.security.mapper.CustomerMapper;
import com.printease.application.security.mapper.ServiceProviderMapper;
import com.printease.application.utils.GeneralMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceProviderService {
    private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final GeneralMessageAccessor generalMessageAccessor;

    private final ServiceProviderRepository serviceProviderRepository;

    public RegistrationResponse registration(@Valid ServiceProviderRegistrationRequest registrationRequest) {
        final ServiceProvider serviceProvider = ServiceProviderMapper.INSTANCE.convertToServiceProvider(registrationRequest);
        serviceProvider.setPassword(bCryptPasswordEncoder.encode(serviceProvider.getPassword()));
        serviceProvider.setAccountCreatedOn(LocalDateTime.now());
        serviceProvider.setAccountUpdatedOn(LocalDateTime.now());
        final String username = registrationRequest.getUsername();

        serviceProviderRepository.save(serviceProvider);

        final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, username);

        log.info("{} registered successfully!", username);

        return new RegistrationResponse(registrationSuccessMessage);

    }
}
