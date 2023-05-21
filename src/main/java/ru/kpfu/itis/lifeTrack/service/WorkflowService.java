package ru.kpfu.itis.lifeTrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lifeTrack.entity.WorkflowEntity;
import ru.kpfu.itis.lifeTrack.exception.Workflow.WorkflowNotFoundException;
import ru.kpfu.itis.lifeTrack.model.Workflow;
import ru.kpfu.itis.lifeTrack.repository.WorkflowRepo;

import java.util.Optional;

@Service
public class WorkflowService {

    private final WorkflowRepo workflowRepo;

    @Autowired
    public WorkflowService(WorkflowRepo workflowRepo) {
        this.workflowRepo = workflowRepo;
    }

    public Workflow getOne(Long id) throws WorkflowNotFoundException {
        Optional<WorkflowEntity> workflowEntity = workflowRepo.findById(id);
        if (workflowEntity.isEmpty()) {
            throw new WorkflowNotFoundException("Workflow with this id does not exist");
        }
        return Workflow.toModel(workflowEntity.get());
    }
}
