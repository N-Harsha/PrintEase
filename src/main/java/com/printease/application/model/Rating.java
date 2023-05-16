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
    private Long rating;
    private String comment;
    @OneToOne
    private AssociatedService associatedService;
    @ManyToOne
    @JsonIgnore
    private ServiceProvider serviceProvider;
    @OneToOne
    private Customer customer;
    private LocalDateTime createdDate;
}
