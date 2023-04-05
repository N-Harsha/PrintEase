package com.example.printease.modals.DTO;

import com.example.printease.modals.Address;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDTO {
    private static final long serialVersionUID = 1L;
    //common for all users types
    String name;
    String email;
    String password;
    String userRole;
    String phoneNumber;
    //specific to the service provider
    String businessName;
    String gstIn;
    Address address;
}
