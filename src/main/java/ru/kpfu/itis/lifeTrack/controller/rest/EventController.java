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
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.dto.request.EventRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.EventResponseDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.service.EventService;

import java.util.Set;

@RestController
@RequestMapping(value = "/users/{userId}/workflows/{workflowId}/projects/{projectId}/events", produces = { "application/json" })
@RequiredArgsConstructor
@Tag(name = "Event")
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
    public ResponseEntity<?> getEventsList(@PathVariable(name = "userId") String userId,
                                        @PathVariable(name = "workflowId") Long workflowId,
                                        @PathVariable(name = "projectId") Long projectId) {
        try {
            Set<EventResponseDto> eventResponseSet = eventService.getEventList(userId, workflowId, projectId);
            return new ResponseEntity<>(eventResponseSet, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> getEvent(@PathVariable(name = "userId") String userId,
                                   @PathVariable(name = "workflowId") Long workflowId,
                                   @PathVariable(name = "projectId") Long projectId,
                                   @PathVariable(name = "eventId") Long eventId) {
        try {
            return new ResponseEntity<>(eventService.getEvent(userId, workflowId, projectId, eventId), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> insertEvent(@PathVariable(name = "userId") String userId,
                                      @PathVariable(name = "workflowId") Long workflowId,
                                      @PathVariable(name = "projectId") Long projectId,
                                      @RequestBody EventRequestDto eventRequestDto) {
        try {
            EventResponseDto eventResponseDto = eventService.insertEvent(userId, workflowId, projectId, eventRequestDto);
            return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> moveEvent(@PathVariable(name = "userId") String userId,
                                    @PathVariable(name = "workflowId") Long workflowId,
                                    @PathVariable(name = "projectId") Long projectId,
                                    @PathVariable(name = "eventId") Long eventId,
                                    @RequestParam("destination") String destination) {
        try {
            EventResponseDto responseDto = eventService.moveEvent(userId, workflowId, projectId, eventId, destination);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> patchEvent(@PathVariable(name = "userId") String userId,
                                     @PathVariable(name = "workflowId") Long workflowId,
                                     @PathVariable(name = "projectId") Long projectId,
                                     @PathVariable(name = "eventId") Long eventId,
                                     @RequestBody JsonPatch patch) {
        try {
            EventResponseDto responseDto = eventService.patchEvent(userId, workflowId, projectId, eventId, patch);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        } catch (JsonPatchException | JsonProcessingException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<?> updateEvent(@PathVariable(name = "userId") String userId,
                                      @PathVariable(name = "workflowId") Long workflowId,
                                      @PathVariable(name = "projectId") Long projectId,
                                      @PathVariable(name = "eventId") Long eventId,
                                      @RequestBody EventRequestDto requestDto) {
        try {
            EventResponseDto responseDto = eventService.updateEvent(userId, workflowId, projectId, eventId, requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> deleteEvent(@PathVariable(name = "userId") String userId,
                                      @PathVariable(name = "workflowId") Long workflowId,
                                      @PathVariable(name = "projectId") Long projectId,
                                      @PathVariable(name = "eventId") Long eventId) {
        try {
            Long deleted = eventService.deleteEvent(userId, workflowId, projectId, eventId);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        }
    }

}
