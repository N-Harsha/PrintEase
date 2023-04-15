package com.printease.application.security.dto;

import com.printease.application.model.ServiceProvider;
import com.printease.application.model.State;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private Integer pincode;

    private State state;

}
