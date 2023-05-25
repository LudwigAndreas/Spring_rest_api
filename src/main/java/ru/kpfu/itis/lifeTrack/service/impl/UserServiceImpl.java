package ru.kpfu.itis.lifeTrack.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lifeTrack.entity.UserEntity;
import ru.kpfu.itis.lifeTrack.exception.User.UserAlreadyExistsException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.mapper.UserMapper;
import ru.kpfu.itis.lifeTrack.model.Role;
import ru.kpfu.itis.lifeTrack.model.User;
import ru.kpfu.itis.lifeTrack.repository.RoleRepo;
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
    private final RoleRepo roleRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserMapper userMapper;

    @Override
    public User insertUser(UserEntity user) throws UserAlreadyExistsException {
        if (userRepo.findUserEntityByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("A user with this username already exists");
        } else if (userRepo.findUserEntityByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("A user with this email already exists");
        }
        user.setCreatedDate(Date.valueOf(LocalDate.now()));
        user.setLastUpdatedDate(user.getCreatedDate());
        return userMapper.toUserModel(userRepo.save(user));
    }

    @Override
    public User getUser(Long id) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("User with this id does not exist");
        }
        log.info("UserService getUserById: " + userEntity.get().getId());
        User out = userMapper.toUserModel(userEntity.get());
        out.setWorkflows(Role.toModel(roleRepo.findAllByUserId(userEntity.get().getId())));
        return out;
    }

    @Override
    public User getUser(String username) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findUserEntityByUsername(username);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("User with this id does not exist");
        }
        log.info("UserService getUserById: " + userEntity.get().getId());
        User out = userMapper.toUserModel(userEntity.get());
        out.setWorkflows(Role.toModel(roleRepo.findAllByUserId(userEntity.get().getId())));
        return out;
    }

    @Override
    public Long deleteUser(Long id) throws UserNotFoundException {
        if (userRepo.findById(id).isEmpty()) {
            throw new UserNotFoundException("User with this id does not exist");
        }
        userRepo.deleteById(id);
        return id;
    }

    @Override
    public User patchUser(Long id, JsonPatch jsonPatch) throws UserNotFoundException, JsonPatchException, JsonProcessingException {
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with this id does not exist");
        }
        UserEntity user = optionalUser.get();
        user.setLastUpdatedDate(Date.valueOf(LocalDate.now()));
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(user, JsonNode.class));
        return userMapper.toUserModel(userRepo.save(objectMapper.treeToValue(patched, UserEntity.class)));
    }

    @Override
    public User updateUser(Long id, UserEntity updated) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with this id does not exist");
        }
        updated.setId(id);
        updated.setCreatedDate(optionalUser.get().getCreatedDate());
        updated.setLastUpdatedDate(Date.valueOf(LocalDate.now()));
        return userMapper.toUserModel(userRepo.save(updated));
    }
}
