package com.printease.application.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMERS")
public class Customer extends User{
    @OneToMany
    private List<ServiceProvider> favouriteServiceProviders;
}
