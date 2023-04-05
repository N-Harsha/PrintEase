package com.example.printease.modals.DTO;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
}
