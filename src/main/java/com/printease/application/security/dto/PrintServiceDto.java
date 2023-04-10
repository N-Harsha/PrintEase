package com.printease.application.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintServiceDto {
    private Long id;
    private String serviceName;
    private String description;
}
