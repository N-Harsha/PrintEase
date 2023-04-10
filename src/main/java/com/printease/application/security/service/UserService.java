package com.printease.application.security.service;

import com.printease.application.security.mapper.ServiceProviderRequestMapper;
import com.printease.application.security.utils.Constants;
import com.printease.application.service.UserRoleService;
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

	private final UserRoleService userRoleService;



	public User findByUsername(String email) {

		return userRepository.findByEmail(email);
	}

	public RegistrationResponse registration(RegistrationRequest registrationRequest) {

		userValidationService.validateUser(registrationRequest);

		if(registrationRequest.getUserRole().getRole().equalsIgnoreCase("customer")){
			registrationRequest.setUserRole(userRoleService.findByRole(Constants.ROLE_CUSTOMER));
			return customerService.registration(registrationRequest);
		}
		else{
			registrationRequest.setUserRole(userRoleService.findByRole(Constants.ROLE_SERVICE_PROVIDER));
			return serviceProviderService.registration(ServiceProviderRequestMapper.INSTANCE.convertToServiceProviderRegistrationRequest(registrationRequest));
		}

	}

	public AuthenticatedUserDto findAuthenticatedUserByUsername(String email) {

		final User user = findByUsername(email);

		return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
	}
}
