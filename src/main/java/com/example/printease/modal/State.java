package com.example.printease.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class State {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @NotNull
    @Size(max = 100)
    String stateName;

}
