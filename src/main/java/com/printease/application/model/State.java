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
public class State {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String stateName;

}
