package com.printease.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalDateTime;

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
    private Float rating;
    private String comment;
    @ManyToOne
    private ServiceProvider serviceProvider;
    @ManyToOne
    private Customer customer;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
