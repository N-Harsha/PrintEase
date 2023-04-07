package com.printease.application.security.service;

import com.printease.application.security.mapper.ServiceProviderRequestMapper;
import com.printease.application.service.UserValidationService;
import com.printease.application.model.User;
import com.printease.application.model.UserRole;
import com.printease.application.security.dto.AuthenticatedUserDto;
import com.printease.application.security.dto.RegistrationRequest;
import com.printease.application.security.dto.RegistrationResponse;
import com.printease.application.security.mapper.UserMapper;
import com.printease.application.utils.GeneralMessageAccessor;
import com.printease.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService{



	private final UserRepository userRepository;

	private final ServiceProviderService serviceProviderService;

	private final CustomerService customerService;


	private final UserValidationService userValidationService;



	public User findByUsername(String username) {

		return userRepository.findByUsername(username);
	}

	public RegistrationResponse registration(RegistrationRequest registrationRequest) {

		userValidationService.validateUser(registrationRequest);

		if(registrationRequest.getUserRole().equalsIgnoreCase("customer")){
			registrationRequest.setUserRole(UserRole.ROLE_CUSTOMER.toString());
			return customerService.registration(registrationRequest);
		}
		else{
			registrationRequest.setUserRole(UserRole.ROLE_SERVICE_PROVIDER.toString());
			return serviceProviderService.registration(ServiceProviderRequestMapper.INSTANCE.convertToServiceProviderRegistrationRequest(registrationRequest));
		}

	}

	public AuthenticatedUserDto findAuthenticatedUserByUsername(String username) {

		final User user = findByUsername(username);

		return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
	}
}
