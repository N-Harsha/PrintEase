package com.example.printease.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Customer extends User{
    @OneToMany
    private List<ServiceProvider> favouriteServiceProviders;


}
