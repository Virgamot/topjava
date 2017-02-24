package ru.javawebinar.topjava.util.exception;

/**
 * Created by Ivan on 24.02.2017.
 */
public class InvalidEntityException extends RuntimeException{
    public InvalidEntityException(String message) {
        super(message);
    }
}
