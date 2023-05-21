package ru.kpfu.itis.lifeTrack.exception.User;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
