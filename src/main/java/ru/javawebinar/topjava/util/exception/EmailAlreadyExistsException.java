package ru.javawebinar.topjava.util.exception;

/**
 * Created by Ivan on 24.02.2017.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("User with this email already present in application");
    }
}
