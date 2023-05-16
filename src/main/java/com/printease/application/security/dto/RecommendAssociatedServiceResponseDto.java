package com.printease.application.security.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendAssociatedServiceResponseDto {
    private List<AssociatedServiceDto> associatedServices;
    private Long serviceProviderId;
    private String serviceProviderName;
    private String serviceProviderBusinessName;
    private Double distance;
/*
* later add the count of number of orders the service provider is working currently
* add the rating of the service provider
* add the number of reviews of the service provider
* add the number of orders the service provider has completed
* add the number of orders the service provider has cancelled
* add the number of orders the service provider has rejected
* add the number of orders the service provider has accepted
*
* add the number of orders the service provider has completed on time
* add the number of orders the service provider has completed late
* add the number of orders the service provider has completed very late
* */
}
