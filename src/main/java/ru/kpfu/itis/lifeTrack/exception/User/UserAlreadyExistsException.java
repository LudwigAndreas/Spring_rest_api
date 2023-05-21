package ru.kpfu.itis.lifeTrack.exception.User;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
