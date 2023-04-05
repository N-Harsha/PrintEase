package com.example.printease.modal.DTO;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDTO {
    private static final long serialVersionUID = 1L;
    String name;
    String email;
    String password;
    String businessName;
    String GSTIN;

}
