package org.example.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
