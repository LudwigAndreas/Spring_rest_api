package ru.kpfu.itis.lifeTrack.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.dto.request.ProjectRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.ProjectResponseDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.service.ProjectService;

import java.util.Set;

@RestController
@RequestMapping("/users/{user_id}/workflows/{workflow_id}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProjectsList(@PathVariable(name = "user_id") String userId,
                                         @PathVariable(name = "workflow_id") Long workflowId) {
        try {
            Set<ProjectResponseDto> projectEntitySet = projectService.getProjectList(userId, workflowId);
            return new ResponseEntity<>(projectEntitySet, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProject(@PathVariable(name = "user_id") String userId,
                                     @PathVariable(name = "workflow_id") Long workflowId,
                                     @PathVariable Long id) {
        try {
            return ResponseEntity.ok(projectService.getProject(userId, workflowId, id));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity insertProject(@PathVariable(name = "user_id") String userId,
                                        @PathVariable(name = "workflow_id") Long workflowId,
                                        @RequestBody ProjectRequestDto projectRequest) {
        try {
            ProjectResponseDto projectDto = projectService.insertProject(userId, workflowId, projectRequest);
            return ResponseEntity.ok(projectDto);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PATCH)
    public ResponseEntity patchProject(@PathVariable(name = "user_id") String userId,
                                       @PathVariable(name = "workflow_id") Long workflowId,
                                       @PathVariable Long id,
                                       @RequestBody JsonPatch patch) {
        try {
            ProjectResponseDto projectDto = projectService.patchProject(userId, workflowId, id, patch);
            return new ResponseEntity<>(projectDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (JsonPatchException | JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity updateProject(@PathVariable(name = "user_id") String userId,
                                        @PathVariable(name = "workflow_id") Long workflowId,
                                        @PathVariable Long id,
                                        @RequestBody ProjectRequestDto projectRequest) {
        try {
            ProjectResponseDto projectDto = projectService.updateProject(userId, workflowId, id,  projectRequest);
            return new ResponseEntity<>(projectDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteProject(@PathVariable(name = "user_id") String userId,
                                        @PathVariable(name = "workflow_id") Long workflowId,
                                        @PathVariable Long id) {
        try {
            return new ResponseEntity<>(projectService.deleteProject(userId, workflowId, id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
