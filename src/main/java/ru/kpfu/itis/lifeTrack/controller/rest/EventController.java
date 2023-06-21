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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.dto.request.EventRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.EventResponseDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.security.jwt.JwtUserDetails;
import ru.kpfu.itis.lifeTrack.service.EventService;

import java.util.Set;

@RestController
@RequestMapping(value = "/users/{userId}/workflows/{workflowId}/projects/{projectId}/events", produces = { "application/json" })
@RequiredArgsConstructor
@Tag(name = "Event")
@PreAuthorize("isAuthenticated()")
public class EventController {

    private final EventService eventService;

    @Operation(
            operationId = "get-event-list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "event data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = EventResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "workflow/project was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @GetMapping
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> getEventsList(@AuthenticationPrincipal JwtUserDetails userDetails,
                                           @PathVariable(name = "userId") String userId,
                                           @PathVariable(name = "workflowId") Long workflowId,
                                           @PathVariable(name = "projectId") Long projectId) throws NotFoundException {
        Set<EventResponseDto> eventResponseSet = eventService.getEventList(userId, workflowId, projectId);
        return new ResponseEntity<>(eventResponseSet, HttpStatus.OK);
    }

    @Operation(
            operationId = "get-event",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "event data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = EventResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "workflow/project/event was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @GetMapping("{eventId}")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> getEvent(@AuthenticationPrincipal JwtUserDetails userDetails,
                                      @PathVariable(name = "userId") String userId,
                                      @PathVariable(name = "workflowId") Long workflowId,
                                      @PathVariable(name = "projectId") Long projectId,
                                      @PathVariable(name = "eventId") Long eventId) throws NotFoundException {
        return new ResponseEntity<>(eventService.getEvent(userId, workflowId, projectId, eventId), HttpStatus.OK);
    }

    @Operation(
            operationId = "insert-event",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "event data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = EventResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "workflow/project was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @PostMapping(consumes = "application/json")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> insertEvent(@AuthenticationPrincipal JwtUserDetails userDetails,
                                         @PathVariable(name = "userId") String userId,
                                         @PathVariable(name = "workflowId") Long workflowId,
                                         @PathVariable(name = "projectId") Long projectId,
                                         @RequestBody EventRequestDto eventRequestDto) throws NotFoundException {
        EventResponseDto eventResponseDto = eventService.insertEvent(userId, workflowId, projectId, eventRequestDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
    }

    @Operation(
            operationId = "move-event",
            deprecated = true,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "event data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = EventResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "workflow/project/event/destination was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @PostMapping("{eventId}/move")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> moveEvent(@AuthenticationPrincipal JwtUserDetails userDetails,
                                       @PathVariable(name = "userId") String userId,
                                       @PathVariable(name = "workflowId") Long workflowId,
                                       @PathVariable(name = "projectId") Long projectId,
                                       @PathVariable(name = "eventId") Long eventId,
                                       @RequestParam("destination") String destination) throws NotFoundException {
        EventResponseDto responseDto = eventService.moveEvent(userId, workflowId, projectId, eventId, destination);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
}

    @Operation(
            operationId = "patch-event",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "event data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = EventResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "workflow/project/event/destination was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @PatchMapping(value = "{eventId}", consumes = "application/json")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> patchEvent(@AuthenticationPrincipal JwtUserDetails userDetails,
                                        @PathVariable(name = "userId") String userId,
                                        @PathVariable(name = "workflowId") Long workflowId,
                                        @PathVariable(name = "projectId") Long projectId,
                                        @PathVariable(name = "eventId") Long eventId,
                                        @RequestBody JsonPatch patch) throws JsonPatchException, NotFoundException, JsonProcessingException {
        EventResponseDto responseDto = eventService.patchEvent(userId, workflowId, projectId, eventId, patch);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(
            operationId = "update-event",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "event data",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = EventResponseDto.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "workflow/project/event was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @PutMapping(value = "{eventId}", consumes = "application/json")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> updateEvent(@AuthenticationPrincipal JwtUserDetails userDetails,
                                         @PathVariable(name = "userId") String userId,
                                         @PathVariable(name = "workflowId") Long workflowId,
                                         @PathVariable(name = "projectId") Long projectId,
                                         @PathVariable(name = "eventId") Long eventId,
                                         @RequestBody EventRequestDto requestDto) throws NotFoundException {
        EventResponseDto responseDto = eventService.updateEvent(userId, workflowId, projectId, eventId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(
            operationId = "delete-event",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "deleted resource id",
                            content = {
                                    @Content(
                                            mediaType = "text/plain"
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "workflow/project/event was not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized request"
                    )
            }
    )
    @DeleteMapping("{eventId}")
    @PreAuthorize("#userDetails.id == #userId")
    public ResponseEntity<?> deleteEvent(@AuthenticationPrincipal JwtUserDetails userDetails,
                                         @PathVariable(name = "userId") String userId,
                                         @PathVariable(name = "workflowId") Long workflowId,
                                         @PathVariable(name = "projectId") Long projectId,
                                         @PathVariable(name = "eventId") Long eventId) throws NotFoundException {
        Long deleted = eventService.deleteEvent(userId, workflowId, projectId, eventId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

}
