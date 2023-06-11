package ru.kpfu.itis.lifeTrack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kpfu.itis.lifeTrack.dto.response.UserDto;
import ru.kpfu.itis.lifeTrack.model.user.UserEntity;
import ru.kpfu.itis.lifeTrack.exception.User.UserAlreadyExistsException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;

public interface UserService {

    UserDto getUser(String userId) throws UserNotFoundException;

    UserDto getUserByUsername(String username) throws UserNotFoundException;

    UserDto insertUser(UserEntity userEntity) throws UserAlreadyExistsException;

    UserDto patchUser(String userId, JsonPatch jsonPatch) throws UserNotFoundException, JsonPatchException, JsonProcessingException;

    UserDto updateUser(String userId, UserEntity updatedUserEntity) throws UserNotFoundException;

    String deleteUser(String userId) throws UserNotFoundException;
}
