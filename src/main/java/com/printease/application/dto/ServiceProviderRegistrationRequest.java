package com.printease.application.dto;

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
public class ServiceProviderRegistrationRequest {
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

    @NotEmpty(message = "{registration_user_role_not_empty}")
    private UserRole userRole;

    @NotEmpty(message = "{registration_phone_number_not_empty}")
    @Pattern(regexp = "^[0-9]{10}$", message = "{registration_phone_number_is_not_valid}")
    String phoneNumber;

    //specific to the service provider

    @NotEmpty(message = "{registration_business_name_not_empty}")
    @Pattern(regexp = "^[A-Za-z0-9&.'\\-,()/ ]+$\n", message = "{registration_business_name_is_not_valid}")
    String businessName;

    @NotEmpty(message = "{registration_gst_in_not_empty}")
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}[0-9A-Z]{1}$", message = "{registration_gst_in_is_not_valid}")
    String gstIn;
    @NotEmpty(message = "{registration_address_not_empty}")
    Address address;

}
