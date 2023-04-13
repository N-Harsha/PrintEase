package com.printease.application.security.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendAssociatedServiceResponseDto {
    private AssociatedServiceDto associatedService;
    private Long serviceProviderId;
    private String serviceProviderName;
}
