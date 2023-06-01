package ru.kpfu.itis.lifeTrack.controller.rest;

import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.model.ProjectEntity;

@RestController
@RequestMapping("/users/{user_id}/workflows/{workflow_id}/projects")
public class ProjectController {

    @GetMapping
    public ResponseEntity getAllProjects(@PathVariable(name = "user_id") Long userId,
                                         @PathVariable(name = "workflow_id") Long workflowId) {
        return null;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity getProject(@PathVariable(name = "user_id") Long userId,
                                     @PathVariable(name = "workflow_id") Long workflowId,
                                     @PathVariable Long id) {
        return null;
    }

    @PostMapping(value = "{id}")
    public ResponseEntity insertProject(@PathVariable(name = "user_id") Long userId,
                                        @PathVariable(name = "workflow_id") Long workflowId,
                                        @PathVariable Long id,
                                        @RequestBody ProjectEntity projectEntity) {
        return null;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PATCH)
    public ResponseEntity patchProject(@PathVariable(name = "user_id") Long userId,
                                       @PathVariable(name = "workflow_id") Long workflowId,
                                       @PathVariable Long id,
                                       @RequestBody JsonPatch patch) {
        return null;
    }

    @PutMapping(value = "{id}")
    public ResponseEntity updateProject(@PathVariable(name = "user_id") Long userId,
                                        @PathVariable(name = "workflow_id") Long workflowId,
                                        @PathVariable Long id,
                                        @RequestBody ProjectEntity projectEntity) {
        return null;
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteProject(@PathVariable(name = "user_id") Long userId,
                                        @PathVariable(name = "workflow_id") Long workflowId,
                                        @PathVariable Long id) {
        return null;
    }
}
