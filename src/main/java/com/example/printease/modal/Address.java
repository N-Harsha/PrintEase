package com.example.printease.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String addressLine1;

    @Size(max = 100)
    private String addressLine2;

    @Column(nullable = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String city;

    @Column(nullable = false)
    @NotNull
    @Digits(integer = 5, fraction = 0)
    private Integer zipCode;

    private Float latitude;
    private Float longitude;

    @ManyToOne
    State state;
}