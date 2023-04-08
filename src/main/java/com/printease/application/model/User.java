package com.printease.application.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;


    @Column(nullable = false, unique = true)
    private String username;

	private String password;

	@OneToOne
	private UserRole userRole;

	private String phoneNumber;

	private LocalDateTime accountCreatedOn;
	private LocalDateTime accountUpdatedOn;

}
