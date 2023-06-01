package ru.kpfu.itis.lifeTrack.exception.User;

import ru.kpfu.itis.lifeTrack.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
