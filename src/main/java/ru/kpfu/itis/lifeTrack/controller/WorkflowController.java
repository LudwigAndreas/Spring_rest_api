package ru.kpfu.itis.lifeTrack.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.exception.Workflow.WorkflowNotFoundException;
import ru.kpfu.itis.lifeTrack.service.WorkflowService;

@RestController
@RequestMapping("/workflows")
@Slf4j
public class WorkflowController {

    private final WorkflowService workflowService;

    @Autowired
    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping("{id}")
    public ResponseEntity getOneWorkflow(@PathVariable Long id) {
        log.info("Getting workflow: " + id);
        try {
            return ResponseEntity.ok(workflowService.getOne(id));
        } catch (WorkflowNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
