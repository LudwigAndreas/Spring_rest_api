package ru.kpfu.itis.lifeTrack.service;

import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.model.helpers.AccessRole;

public interface UserManagementService {

    boolean isUserExists(Long userId) throws UserNotFoundException;

    AccessRole getUserRole(Long userId, Long workflowId) throws NotFoundException;
}
