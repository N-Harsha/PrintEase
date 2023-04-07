package com.printease.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SERVICE_PROVIDERS")
public class ServiceProvider extends User{

    @Column(nullable = false, length = 100)
    private String businessName;

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
