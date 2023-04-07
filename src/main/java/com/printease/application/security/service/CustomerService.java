package com.printease.application.security.service;

import com.printease.application.model.Customer;
import com.printease.application.model.User;
import com.printease.application.model.UserRole;
import com.printease.application.repository.CustomerRepository;
import com.printease.application.security.dto.RegistrationRequest;
import com.printease.application.security.dto.RegistrationResponse;
import com.printease.application.security.dto.ServiceProviderRegistrationRequest;
import com.printease.application.security.mapper.CustomerMapper;
import com.printease.application.security.mapper.UserMapper;
import com.printease.application.utils.GeneralMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final GeneralMessageAccessor generalMessageAccessor;

    private final CustomerRepository customerRepository;

    public RegistrationResponse registration( RegistrationRequest registrationRequest) {

        final Customer customer = CustomerMapper.INSTANCE.convertToCustomer(registrationRequest);
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customer.setAccountCreatedOn(LocalDateTime.now());
        customer.setAccountUpdatedOn(LocalDateTime.now());
        final String username = registrationRequest.getUsername();

        customerRepository.save(customer);

        final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, username);

        log.info("{} registered successfully!", username);

        return new RegistrationResponse(registrationSuccessMessage);

    }
}
