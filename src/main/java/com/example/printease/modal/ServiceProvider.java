package com.example.printease.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class ServiceProvider extends User {

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String businessName;

    @NotBlank
    @Size(max = 15,min = 15)
    @Column(nullable = false, length = 15)
    private String GSTIN;

    @OneToMany(mappedBy = "serviceProvider")
    private List<AssociatedService> associatedServices;

    @OneToMany(mappedBy = "serviceProvider")
    private List<Rating> ratings;

    @OneToOne
    private Address address;
}
