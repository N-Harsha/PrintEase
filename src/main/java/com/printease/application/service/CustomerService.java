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
import com.printease.application.security.dto.ServiceProviderDto;
import com.printease.application.security.dto.ServiceProviderRegistrationRequest;
import com.printease.application.security.mapper.CustomerMapper;
import com.printease.application.security.mapper.ServiceProviderRequestMapper;
import com.printease.application.security.mapper.UserMapper;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.GeneralMessageAccessor;
import com.printease.application.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private static final String REGISTRATION_SUCCESSFUL = "registration_successful";
    private static final String USER_NOT_FOUND = "user_not_found";
    private static final String CUSTOMER_NOT_FOUND = "customer_not_found";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final GeneralMessageAccessor generalMessageAccessor;

    private final ExceptionMessageAccessor exceptionMessageAccessor;

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    private final ServiceOfServiceProvider serviceOfServiceProvider;

    public RegistrationResponse registration(RegistrationRequest registrationRequest) {

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

    public ResponseEntity<List<ServiceProviderDto>> getFavoriteServiceProvider(String email) {
        return ResponseEntity.ok(findCustomerByUserEmail(email).getFavouriteServiceProviders().stream()
                .map(ServiceProviderRequestMapper.INSTANCE::convertToServiceProviderDto)
                .collect(Collectors.toList()));
    }

    public ResponseEntity<String> addFavoriteServiceProvider(String name, Long serviceProviderId) {
        Customer customer = findCustomerByUserEmail(name);
        log.info("fetched customer with id : {}", customer.getId());
        ServiceProvider serviceProvider = serviceOfServiceProvider.findById(serviceProviderId);
        log.info("fetched service provider with id : {}", serviceProvider.getId());
        customer.getFavouriteServiceProviders().add(serviceProvider);
        log.info("added service provider with id : {} to customer with id : {}", serviceProvider.getId(), customer.getId());
        if(customer.getFavouriteServiceProviders().contains(serviceProvider)){
            log.info("service provider with id : {} is already added to customer with id : {}", serviceProvider.getId(), customer.getId());
            throw new CustomException(new ApiExceptionResponse(
                    exceptionMessageAccessor.getMessage(null, ProjectConstants.FAVOURITE_SERVICE_PROVIDER_ALREADY_ADDED,
                            serviceProvider.getId(), customer.getId()),
                    HttpStatus.BAD_REQUEST, LocalDateTime.now()));

        }
        customerRepository.save(customer);
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null, ProjectConstants.FAVOURITE_SERVICE_PROVIDER_ADDED,
                serviceProvider.getId(), customer.getId()));
    }


    public ResponseEntity<String> deleteFavoriteServiceProvider(String email, Long serviceProviderId) {
        Customer customer = findCustomerByUserEmail(email);
        log.info("fetched customer with id : {}", customer.getId());
        ServiceProvider serviceProvider = serviceOfServiceProvider.findById(serviceProviderId);
        log.info("fetched service provider with id : {}", serviceProvider.getId());
        customer.getFavouriteServiceProviders().remove(serviceProvider);
        log.info("removed service provider with id : {} from customer with id : {}", serviceProvider.getId(), customer.getId());
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null, ProjectConstants.FAVOURITE_SERVICE_PROVIDER_REMOVED,
                serviceProvider.getId(), customer.getId()));
    }
}
