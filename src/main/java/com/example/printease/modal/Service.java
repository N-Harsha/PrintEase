package com.example.printease.modal;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceName;
    private String description;
    private Float price;


}
