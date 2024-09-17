package com.Sola.resume_creation_service.exception;

public class TemplateNotFoundException extends RuntimeException{
    public TemplateNotFoundException(String message){
        super(message);
    }
}
