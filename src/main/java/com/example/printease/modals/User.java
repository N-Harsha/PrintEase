package com.example.printease.modals;

import com.example.printease.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Name should contain only alphabets and spaces")
    private String name;

    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

// todo add username feature in the future.
//    @NotBlank
//    @Size(max = 50)
//    @Column(nullable = false, length = 50)
//    private String userName;

    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    private String password;

//    @ManyToOne
//    private UserRole userRole; todo later update the string user role to a list of user roles and migrate the user roles to a separate table.
    @Enumerated(EnumType.STRING)
    private UserRoles userRole;

    @Size(min = 10,max = 10, message = "Phone number should be of 10 digits")
    @Column(length = 10)
    private String phoneNumber;

//  private String alternatePhoneNumber; todo add alternate phone number feature in the future.

    private LocalDateTime accountCreatedOn;
    private LocalDateTime accountUpdatedOn;

//    @NotNull
//    @Column(nullable = false)
//    private Boolean isActive;


}