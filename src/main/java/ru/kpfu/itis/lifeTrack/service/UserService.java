package ru.kpfu.itis.lifeTrack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import ru.kpfu.itis.lifeTrack.entity.UserEntity;
import ru.kpfu.itis.lifeTrack.exception.User.UserAlreadyExistsException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.model.User;
import ru.kpfu.itis.lifeTrack.repository.UserRepo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User insertUser(UserEntity user) throws UserAlreadyExistsException {
        if (userRepo.findUserEntityByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("A user with this username already exists");
        } else if (userRepo.findUserEntityByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("A user with this email already exists");
        }
        user.setCreatedDate(Date.valueOf(LocalDate.now()));
        user.setLastUpdatedDate(user.getCreatedDate());
        return User.toModel(userRepo.save(user));
    }

    public User getUserById(Long id) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("User with this id does not exist");
        }
//        log.info("Users role: " + userEntity.get().getWorkflows());
        return User.toModel(userEntity.get());
    }

    public Long deleteUserById(Long id) throws UserNotFoundException {
        if (userRepo.findById(id).isEmpty()) {
            throw new UserNotFoundException("User with this id does not exist");
        }
        userRepo.deleteById(id);
        return id;
    }

    public User patchUser(Long id, JsonPatch jsonPatch) throws UserNotFoundException, JsonPatchException, JsonProcessingException {
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with this id does not exist");
        }
        UserEntity user = optionalUser.get();
        user.setLastUpdatedDate(Date.valueOf(LocalDate.now()));
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(user, JsonNode.class));
        return User.toModel(userRepo.save(objectMapper.treeToValue(patched, UserEntity.class)));
    }

    public User updateUser(Long id, UserEntity updated) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with this id does not exist");
        }
        updated.setId(id);
        updated.setCreatedDate(optionalUser.get().getCreatedDate());
        updated.setLastUpdatedDate(Date.valueOf(LocalDate.now()));
        return User.toModel(userRepo.save(updated));
    }
}
