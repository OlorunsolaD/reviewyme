package com.Sola.resume_creation_service.exception;

public class ResumeNotFoundException extends RuntimeException{

    public ResumeNotFoundException (String message){
        super(message);
    }
}
