package com.printease.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintServiceDto {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String serviceName;
    private String description;
}
