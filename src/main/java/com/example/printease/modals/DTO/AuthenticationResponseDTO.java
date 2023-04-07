package com.example.printease.modals.DTO;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseDTO {
    private static final long serialVersionUID = 1L;
    private String token;
    private String email;
    private String name;
    private String userRole;
}
