package com.printease.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderDto {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String businessName;

    private String gstIn;

    private List<AssociatedServiceDto> associatedServices;

//    private List<Rating> ratings;

    private AddressDto address;
}
