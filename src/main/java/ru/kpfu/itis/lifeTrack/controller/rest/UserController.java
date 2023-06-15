package ru.kpfu.itis.lifeTrack.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.dto.response.EventResponseDto;
import ru.kpfu.itis.lifeTrack.dto.response.UserDto;
import ru.kpfu.itis.lifeTrack.model.user.UserEntity;
import ru.kpfu.itis.lifeTrack.exception.user.UserAlreadyExistsException;
import ru.kpfu.itis.lifeTrack.exception.user.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.service.UserService;
import ru.kpfu.itis.lifeTrack.service.impl.UserServiceImpl;

import java.net.http.HttpResponse;

@Tag(name = "User")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = { "application/json" })
public class UserController {

    private final UserService userService;

    @Operation(
            operationId = "get-user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "user data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = UserDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @GetMapping(value = "{userId}")
    @PreAuthorize("# == ")
    public ResponseEntity<?> getUser(@PathVariable(name = "userId") String userId) {
        try {
            UserDto userDto = userService.getUser(userId);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY,HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "insert-user",
            deprecated = true,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "user data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = UserDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<?> insertUser(@RequestBody UserEntity userEntity) {
        try {
            UserDto userDto = userService.insertUser(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.CONFLICT);
        }
    }

    @Operation(
            operationId = "patch-user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "user data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = UserDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @RequestMapping(value = "{userId}", method = RequestMethod.PATCH, consumes = {"application/json"})
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<?> patchUser(@PathVariable(name = "userId") String userId,
                                             @RequestBody JsonPatch jsonPatch) {
        try {
            UserDto user = userService.patchUser(userId, jsonPatch);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        } catch (JsonPatchException | JsonProcessingException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            operationId = "update-user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "user data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = UserDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @PutMapping(value = "{userId}", consumes = {"application/json"})
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<?> updateUser(@PathVariable(name = "userId") String userId,
                                              @RequestBody UserEntity updated) {
        try {
            UserDto user = userService.updateUser(userId, updated);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "delete-user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "deleted user id",
                            content = {
                                    @Content(
                                            mediaType = "text/plain"
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @DeleteMapping(value = "{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") String userId) {
        try {
            return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
    }

}
