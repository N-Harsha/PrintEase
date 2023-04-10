package com.printease.application.security.dto;

import com.printease.application.model.Address;
import com.printease.application.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class RegistrationRequest {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "{registration_name_not_empty}")
	@Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{registration_name_is_not_valid}")
	private String name;

	@Email(message = "{registration_email_is_not_valid}")
	@NotEmpty(message = "{registration_email_not_empty}")
	private String email;

	@NotEmpty(message = "{registration_password_not_empty}")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "{registration_password_is_not_valid}")
	private String password;

	private UserRole userRole;

	@NotEmpty(message = "{registration_phone_number_not_empty}")
	@Pattern(regexp = "^[0-9]{10}$", message = "{registration_phone_number_is_not_valid}")
	String phoneNumber;

	//specific to the service provider
	String businessName;
	String gstIn;
	Address address;


}
