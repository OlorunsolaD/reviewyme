package com.Sola.resume_upload_service.exception;

public class resumeNotFoundException extends RuntimeException{
    public resumeNotFoundException(String message){
        super(message);
    }
}
