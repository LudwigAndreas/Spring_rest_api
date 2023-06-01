package ru.kpfu.itis.lifeTrack.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.dto.response.WorkflowDto;
import ru.kpfu.itis.lifeTrack.service.impl.WorkflowServiceImpl;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users/{user_id}/workflows")
@Slf4j
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowServiceImpl workflowService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getWorkflowList(@PathVariable Long user_id) {
        try {
            Set<WorkflowDto> workflowDtoList = workflowService.getWorkflowList(user_id);
            return new ResponseEntity<>(workflowDtoList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value ="{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getWorkflow(@PathVariable(name = "user_id") Long userId,
                                      @PathVariable Long id) {
        try {
            return ResponseEntity.ok(workflowService.getWorkflow(userId, id));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertWorkflow(@PathVariable Long user_id,
                                         @RequestBody WorkflowDto workflowRequest) {
        try {
            WorkflowDto workflow = workflowService.insertWorkflow(user_id, workflowRequest);
            return ResponseEntity.ok(workflow);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @RequestMapping(value = "{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patchWorkflow(@PathVariable Long user_id,
                                        @PathVariable Long id,
                                        @RequestBody JsonPatch patch) {
        try {
            WorkflowDto workflowDto = workflowService.patchWorkflow(user_id, id, patch);
            return new ResponseEntity<>(workflowDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (JsonPatchException | JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateWorkflow(@PathVariable(name = "user_id") Long userId,
                                         @PathVariable Long id,
                                         @RequestBody WorkflowDto workflowRequest) {
        try {
            WorkflowDto workflowDto = workflowService.updateWorkflow(userId, id, workflowRequest);
            return new ResponseEntity<>(workflowDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteWorkflow(@PathVariable(name = "user_id") Long userId,
                                         @PathVariable Long id) {
        try {
            return new ResponseEntity<>(workflowService.deleteWorkflow(userId, id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
