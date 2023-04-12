package com.printease.application.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoServiceProvider extends OrderDto{
    private static final long serialVersionUID = 1L;
    private String customerName;
}
