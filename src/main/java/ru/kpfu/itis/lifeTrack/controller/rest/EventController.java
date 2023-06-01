package ru.kpfu.itis.lifeTrack.controller.rest;

import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{user_id}/workflows/{workflow_id}/projects/{project_id}/events")
public class EventController {

    @DeleteMapping("{id}")
    public ResponseEntity deleteEvent(@PathVariable(name = "user_id") Long userId,
                                      @PathVariable(name = "workflow_id") Long workflowId,
                                      @PathVariable(name = "project_id") Long projectId,
                                      @PathVariable Long id) {
        return null;
    }

    @GetMapping
    public ResponseEntity getEventsList(@PathVariable(name = "user_id") Long userId,
                                        @PathVariable(name = "workflow_id") Long workflowId,
                                        @PathVariable(name = "project_id") Long projectId) {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity getEvent(@PathVariable(name = "user_id") Long userId,
                                   @PathVariable(name = "workflow_id") Long workflowId,
                                   @PathVariable(name = "project_id") Long projectId,
                                   @PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity insertEvent() {
        return null;
    }

    @PostMapping("{id}/move")
    public ResponseEntity moveEvent() {
        return null;
    }

    @PatchMapping("{id}")
    public ResponseEntity patchEvent(@RequestBody JsonPatch patch) {
        return null;
    }

    @PutMapping
    public ResponseEntity updateEvent() {
        return null;
    }

}
