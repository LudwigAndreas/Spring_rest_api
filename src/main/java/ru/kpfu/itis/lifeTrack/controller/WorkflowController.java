package ru.kpfu.itis.lifeTrack.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.entity.WorkflowEntity;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.exception.Workflow.WorkflowNotFoundException;
import ru.kpfu.itis.lifeTrack.model.Workflow;
import ru.kpfu.itis.lifeTrack.service.impl.WorkflowServiceImpl;

@RestController
@RequestMapping("/workflows")
@Slf4j
public class WorkflowController {

    private final WorkflowServiceImpl workflowService;

    @Autowired
    public WorkflowController(WorkflowServiceImpl workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping("{id}")
    public ResponseEntity getOneWorkflow(@PathVariable Long id) {
        log.info("Getting workflow: " + id);
        try {
            return ResponseEntity.ok(workflowService.getWorkflow(id));
        } catch (WorkflowNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "{user_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertWorkflow(@PathVariable Long user_id, @RequestBody WorkflowEntity workflowEntity) {
        log.debug("Creating new workflow for user: " + user_id);
        try {
            Workflow workflow = workflowService.insertWorkflow(user_id, workflowEntity);
            return ResponseEntity.ok(workflow);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
