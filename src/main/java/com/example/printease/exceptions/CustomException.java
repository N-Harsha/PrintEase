package com.example.printease.exceptions;


import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1488638787124982982L;

    public CustomException(){super();}

    public CustomException(String message){super(message);}

    public CustomException(Throwable cause){super(cause);}

    public CustomException(String message,Throwable cause){super(message, cause);}


    public CustomException(String message, HttpStatus status){super(message);}
}
