package com.example.printease.modals;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Float rate;

    @ManyToOne
    @JoinColumn(name = "associated_service_id",nullable = false)
    private AssociatedService associatedService;
}
