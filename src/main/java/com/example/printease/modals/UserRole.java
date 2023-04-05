package com.example.printease.modals;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

//@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //todo try to change the id to userRoleId
    private Long id;
    @Column(unique = true,nullable = false)
    @NotNull
    @Pattern(regexp = "^ROLE_", message = "role should begin with ROLE_")
    private String role;


}
