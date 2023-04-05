package com.example.printease.modal.DTO;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseDTO {
    private static final long serialVersionUID = 1L;
    private String token;
}
