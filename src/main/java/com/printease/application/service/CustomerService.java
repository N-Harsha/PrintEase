package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.Customer;
import com.printease.application.model.ServiceProvider;
import com.printease.application.model.User;
import com.printease.application.model.UserRole;
import com.printease.application.repository.CustomerRepository;
import com.printease.application.repository.UserRepository;
import com.printease.application.security.dto.RegistrationRequest;
import com.printease.application.security.dto.RegistrationResponse;
import com.printease.application.security.dto.ServiceProviderRegistrationRequest;
import com.printease.application.security.mapper.CustomerMapper;
import com.printease.application.security.mapper.UserMapper;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.GeneralMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private static final String REGISTRATION_SUCCESSFUL = "registration_successful";
    private static final String USER_NOT_FOUND = "user_not_found";
    private static final String CUSTOMER_NOT_FOUND = "customer_not_found";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final GeneralMessageAccessor generalMessageAccessor;

    private  final ExceptionMessageAccessor exceptionMessageAccessor;

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    public RegistrationResponse registration( RegistrationRequest registrationRequest) {

        final Customer customer = CustomerMapper.INSTANCE.convertToCustomer(registrationRequest);
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customer.setAccountCreatedOn(LocalDateTime.now());
        customer.setAccountUpdatedOn(LocalDateTime.now());
        final String email = registrationRequest.getEmail();

        customerRepository.save(customer);

        final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, email);

        log.info("{} registered successfully!", email);

        return new RegistrationResponse(registrationSuccessMessage);

    }

    public Customer findCustomerByUserEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null, USER_NOT_FOUND, email),
                        HttpStatus.BAD_REQUEST, LocalDateTime.now())));
        return customerRepository.findById(user.getId()).orElseThrow(() -> new CustomException(new ApiExceptionResponse(
                exceptionMessageAccessor.getMessage(null, CUSTOMER_NOT_FOUND, email),
                HttpStatus.BAD_REQUEST, LocalDateTime.now())));
    }

}
