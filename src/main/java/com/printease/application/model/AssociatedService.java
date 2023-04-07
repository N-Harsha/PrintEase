package com.printease.application.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ASSOCIATED_SERVICES")
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
