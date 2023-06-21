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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.dto.response.EventResponseDto;
import ru.kpfu.itis.lifeTrack.dto.response.UserDto;
import ru.kpfu.itis.lifeTrack.model.user.UserEntity;
import ru.kpfu.itis.lifeTrack.exception.user.UserAlreadyExistsException;
import ru.kpfu.itis.lifeTrack.exception.user.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.security.jwt.JwtUserDetails;
import ru.kpfu.itis.lifeTrack.service.UserService;
import ru.kpfu.itis.lifeTrack.service.impl.UserServiceImpl;

import java.net.http.HttpResponse;

@Tag(name = "User")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = { "application/json" })
@PreAuthorize("isAuthenticated()")
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
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal JwtUserDetails userDetails,
                                     @PathVariable(name = "userId") String userId) throws UserNotFoundException {
        UserDto userDto = userService.getUser(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
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
    public ResponseEntity<?> insertUser(@AuthenticationPrincipal JwtUserDetails userDetails,
                                        @RequestBody UserEntity userEntity) throws UserAlreadyExistsException {
        UserDto userDto = userService.insertUser(userEntity);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
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
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> patchUser(@AuthenticationPrincipal JwtUserDetails userDetails,
                                       @PathVariable(name = "userId") String userId,
                                       @RequestBody JsonPatch jsonPatch) throws UserNotFoundException, JsonPatchException, JsonProcessingException {
        UserDto user = userService.patchUser(userId, jsonPatch);
        return new ResponseEntity<>(user, HttpStatus.OK);
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
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal JwtUserDetails userDetails,
                                        @PathVariable(name = "userId") String userId,
                                        @RequestBody UserEntity updated) throws UserNotFoundException {
        UserDto user = userService.updateUser(userId, updated);
        return new ResponseEntity<>(user, HttpStatus.OK);
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
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal JwtUserDetails userDetails,
                                        @PathVariable(name = "userId") String userId) throws UserNotFoundException {
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
}

}
