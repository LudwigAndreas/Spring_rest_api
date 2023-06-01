package ru.kpfu.itis.lifeTrack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ru.kpfu.itis.lifeTrack.dto.response.UserDto;
import ru.kpfu.itis.lifeTrack.model.UserEntity;
import ru.kpfu.itis.lifeTrack.exception.User.UserAlreadyExistsException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;

public interface UserService {

    UserDto getUser(Long id) throws UserNotFoundException;

    UserDto getUser(String username) throws UserNotFoundException;

    UserDto insertUser(UserEntity userEntity) throws UserAlreadyExistsException;

    UserDto patchUser(Long id, JsonPatch jsonPatch) throws UserNotFoundException, JsonPatchException, JsonProcessingException;

    UserDto updateUser(Long id, UserEntity updatedUserEntity) throws UserNotFoundException;

    Long deleteUser(Long id) throws UserNotFoundException;
}
