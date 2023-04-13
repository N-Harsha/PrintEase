package com.printease.application.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESSES")
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String addressLine1;

    private String addressLine2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Integer pincode;

    private Double latitude;
    private Double longitude;

    @ManyToOne
    private State state;

    @OneToOne(mappedBy = "address")
    private ServiceProvider serviceProvider;
}