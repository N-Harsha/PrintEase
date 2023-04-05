package com.example.printease.modal;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long rating;
    private String comment;
    @OneToOne
    private Service service;
    @ManyToOne
    private ServiceProvider serviceProvider;
    @OneToOne
    private Customer customer;

}
