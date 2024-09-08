package com.Sola.Resume_Service.exception;

public class resumeNotFoundException extends RuntimeException{
    public resumeNotFoundException(String message){
        super(message);
    }
}
