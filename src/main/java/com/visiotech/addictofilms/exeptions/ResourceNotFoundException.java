package com.visiotech.addictofilms.exeptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message); // On appelle le constructeur de RuntimeException
    }
}

