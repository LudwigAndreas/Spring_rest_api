package ru.kpfu.itis.lifeTrack.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.dto.request.EventRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.EventResponseDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.service.EventService;

import java.util.Set;

@RestController
@RequestMapping("/users/{user_id}/workflows/{workflow_id}/projects/{project_id}/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity getEventsList(@PathVariable(name = "user_id") Long userId,
                                        @PathVariable(name = "workflow_id") Long workflowId,
                                        @PathVariable(name = "project_id") Long projectId) {
        try {
            Set<EventResponseDto> eventResponseSet = eventService.getEventList(userId, workflowId, projectId);
            return new ResponseEntity<>(eventResponseSet, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getEvent(@PathVariable(name = "user_id") Long userId,
                                   @PathVariable(name = "workflow_id") Long workflowId,
                                   @PathVariable(name = "project_id") Long projectId,
                                   @PathVariable Long id) {
        try {
            return new ResponseEntity(eventService.getEvent(userId, workflowId, projectId, id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity insertEvent(@PathVariable(name = "user_id") Long userId,
                                      @PathVariable(name = "workflow_id") Long workflowId,
                                      @PathVariable(name = "project_id") Long projectId,
                                      @RequestBody EventRequestDto eventRequestDto) {
        try {
            EventResponseDto eventResponseDto = eventService.insertEvent(userId, workflowId, projectId, eventRequestDto);
            return new ResponseEntity(eventResponseDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{id}/move")
    public ResponseEntity moveEvent(@PathVariable(name = "user_id") Long userId,
                                    @PathVariable(name = "workflow_id") Long workflowId,
                                    @PathVariable(name = "project_id") Long projectId,
                                    @PathVariable(name = "id") Long eventId,
                                    @RequestParam("destination") String destination) {
        try {
            EventResponseDto responseDto = eventService.moveEvent(userId, workflowId, projectId, eventId, destination);
            return new ResponseEntity(responseDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity patchEvent(@PathVariable(name = "user_id") Long userId,
                                     @PathVariable(name = "workflow_id") Long workflowId,
                                     @PathVariable(name = "project_id") Long projectId,
                                     @PathVariable(name = "id") Long eventId,
                                     @RequestBody JsonPatch patch) {
        try {
            EventResponseDto responseDto = eventService.patchEvent(userId, workflowId, projectId, eventId, patch);
            return new ResponseEntity(responseDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (JsonPatchException | JsonProcessingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity updateEvent(@PathVariable(name = "user_id") Long userId,
                                      @PathVariable(name = "workflow_id") Long workflowId,
                                      @PathVariable(name = "project_id") Long projectId,
                                      @PathVariable(name = "id") Long eventId,
                                      @RequestBody EventRequestDto requestDto) {
        try {
            EventResponseDto responseDto = eventService.updateEvent(userId, workflowId, projectId, eventId, requestDto);
            return new ResponseEntity(responseDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEvent(@PathVariable(name = "user_id") Long userId,
                                      @PathVariable(name = "workflow_id") Long workflowId,
                                      @PathVariable(name = "project_id") Long projectId,
                                      @PathVariable Long id) {
        try {
            Long deleted = eventService.deleteEvent(userId, workflowId, projectId, id);
            return new ResponseEntity(deleted, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
