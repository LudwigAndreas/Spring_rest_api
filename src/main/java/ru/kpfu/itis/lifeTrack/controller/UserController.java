package ru.kpfu.itis.lifeTrack.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.entity.UserEntity;
import ru.kpfu.itis.lifeTrack.exception.User.UserAlreadyExistsException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.model.User;
import ru.kpfu.itis.lifeTrack.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        log.debug("Deleting user: " + id);
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        log.debug("Getting user: " + id);
        try {
            User user = userService.getUser(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertUser(@RequestBody UserEntity userEntity) {
        log.debug("Creating user: " + userEntity.getUsername());
        try {
            User user = userService.insertUser(userEntity);
            return ResponseEntity.ok(user);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patchUser(@PathVariable Long id,   @RequestBody JsonPatch jsonPatch) {
        log.debug("Patching user: " + id);
        try {
            User user = userService.patchUser(id, jsonPatch);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException | JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserEntity updated) {
        log.debug("Updating user: " + id);
        try {
            User user = userService.updateUser(id, updated);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
