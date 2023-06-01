package ru.kpfu.itis.lifeTrack.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lifeTrack.dto.response.UserDto;
import ru.kpfu.itis.lifeTrack.model.UserEntity;
import ru.kpfu.itis.lifeTrack.exception.User.UserAlreadyExistsException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.mapper.UserMapper;
import ru.kpfu.itis.lifeTrack.repository.UserRepo;
import ru.kpfu.itis.lifeTrack.service.UserService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserMapper userMapper;

    @Override
    public UserDto insertUser(UserEntity user) throws UserAlreadyExistsException {
        if (userRepo.findUserEntityByUsername(user.getUsername()).isPresent()) {
            log.warn("IN insertUser: user with {} username already exists", user.getUsername());
            throw new UserAlreadyExistsException("A user with this username already exists");
        } else if (userRepo.findUserEntityByEmail(user.getEmail()).isPresent()) {
            log.warn("IN insertUser: user with {} email already exists", user.getEmail());
            throw new UserAlreadyExistsException("A user with this email already exists");
        }

        log.info("IN insertUser - user: {} successfully registered", user);

        return userMapper.EntityToDto(userRepo.save(user));
    }

    @Override
    public UserDto getUser(Long id) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        if (userEntity.isEmpty()) {
            log.warn("IN getUser: User with {} id was not found", id);
            throw new UserNotFoundException("User with this id does not exist");
        }

        UserDto out = userMapper.EntityToDto(userEntity.get());

        log.info("IN getUser - user with id: {} successfully found", out.getId());

        return out;
    }

    @Override
    public UserDto getUser(String username) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findUserEntityByUsername(username);
        if (userEntity.isEmpty()) {
            log.warn("IN getUser: User with {} username was not found", username);
            throw new UserNotFoundException("User with this id does not exist");
        }

        UserDto out = userMapper.EntityToDto(userEntity.get());

        log.info("IN getUser - user with username: {} successfully found", username);

        return out;
    }

    @Override
    public Long deleteUser(Long id) throws UserNotFoundException {
        if (userRepo.findById(id).isEmpty()) {
            log.warn("IN deleteUser - user with id: {} was not found", id);
            throw new UserNotFoundException("User with this id does not exist");
        }
        userRepo.deleteById(id);

        log.info("IN deleteUser - user with id: {} successfully deleted", id);

        return id;
    }

    @Override
    public UserDto patchUser(Long id, JsonPatch jsonPatch) throws UserNotFoundException, JsonPatchException, JsonProcessingException {
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            log.warn("IN patchUser - user with id: {} was not found", id);
            throw new UserNotFoundException("User with this id does not exist");
        }
        UserEntity user = optionalUser.get();
        user.setLastUpdatedDate(Date.valueOf(LocalDate.now()));
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(user, JsonNode.class));

        log.info("IN patchUser - user with id: {} successfully patched", id);

        return userMapper.EntityToDto(userRepo.save(objectMapper.treeToValue(patched, UserEntity.class)));
    }

    @Override
    public UserDto updateUser(Long id, UserEntity updated) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            log.warn("IN updateUser - user with id: {} was not found", id);
            throw new UserNotFoundException("User with this id does not exist");
        }
        updated.setId(id);

        log.info("IN updateUser - user with id: {} was successfully updated", id);

        return userMapper.EntityToDto(userRepo.save(updated));
    }
}
