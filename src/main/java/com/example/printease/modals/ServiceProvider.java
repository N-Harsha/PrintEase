package com.example.printease.modals;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ServiceProvider extends User {

    @NotBlank(message = "Business name is required")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String businessName;

    @NotBlank(message = "gstIn is required")
    @Size(max = 15,min = 15)
    @Column(nullable = false, length = 15)
    private String gstIn;

    @OneToMany(mappedBy = "serviceProvider")
    private List<AssociatedService> associatedServices;

    @OneToMany(mappedBy = "serviceProvider")
    private List<Rating> ratings;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
