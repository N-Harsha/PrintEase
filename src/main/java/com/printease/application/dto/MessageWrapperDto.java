package com.printease.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class MessageWrapperDto {
    private static final long serialVersionUID = 1L;
    public MessageWrapperDto(String message){
        this.message = message;
    }
    private String message;
}
