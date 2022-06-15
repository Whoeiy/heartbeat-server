package com.example.heartbeatserver.util;

public class ParamaErrorException extends RuntimeException{
    public ParamaErrorException() {

    }

    public ParamaErrorException(String message) {
        super(message);
    }
}
