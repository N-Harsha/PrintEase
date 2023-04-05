package com.example.printease.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

// todo add username feature in the future.
//    @NotBlank
//    @Size(max = 50)
//    @Column(nullable = false, length = 50)
//    private String userName;

    @NotBlank
    @Column(nullable = false)
    private String password;

//    @ManyToOne
//    private UserRole userRole; todo later update the string user role to a list of user roles and migrate the user roles to a separate table.
    private String userRole;

    @Size(max = 20)
    @Column(length = 20)
    private String phoneNumber;

//  private String alternatePhoneNumber; todo add alternate phone number feature in the future.

    private LocalDateTime accountCreatedOn;
    private LocalDateTime accountUpdatedOn;

//    @NotNull
//    @Column(nullable = false)
//    private Boolean isActive;


}