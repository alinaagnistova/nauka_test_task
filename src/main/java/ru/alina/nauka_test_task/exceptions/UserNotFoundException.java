package ru.alina.nauka_test_task.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("User with id " + id + " not found");
    }
}
