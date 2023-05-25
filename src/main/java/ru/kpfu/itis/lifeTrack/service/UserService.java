package ru.kpfu.itis.lifeTrack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ru.kpfu.itis.lifeTrack.entity.UserEntity;
import ru.kpfu.itis.lifeTrack.exception.User.UserAlreadyExistsException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.model.User;

public interface UserService {

    User getUser(Long id) throws UserNotFoundException;

    User getUser(String username) throws UserNotFoundException;

    User insertUser(UserEntity userEntity) throws UserAlreadyExistsException;

    User patchUser(Long id, JsonPatch jsonPatch) throws UserNotFoundException, JsonPatchException, JsonProcessingException;

    User updateUser(Long id, UserEntity updatedUserEntity) throws UserNotFoundException;

    Long deleteUser(Long id) throws UserNotFoundException;
}
