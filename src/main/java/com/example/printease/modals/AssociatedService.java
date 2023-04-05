package com.example.printease.modals;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociatedService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Service service;

    @OneToMany(mappedBy = "associatedService")
    private List<Offer> offerList;

    @ManyToOne
    @JoinColumn(name = "service_provider_id",nullable = false)
    private ServiceProvider serviceProvider;
}
