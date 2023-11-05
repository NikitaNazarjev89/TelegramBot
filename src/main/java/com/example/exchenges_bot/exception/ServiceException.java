package com.example.exchenges_bot.exception;

public class ServiceException extends Exception{

    public ServiceException(String message, Throwable cause){
       super(message,cause);
    }
}
