package org.example.exception;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
