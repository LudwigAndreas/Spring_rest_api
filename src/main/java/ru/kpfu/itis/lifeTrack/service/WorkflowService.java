package ru.kpfu.itis.lifeTrack.service;

import com.github.fge.jsonpatch.JsonPatch;
import ru.kpfu.itis.lifeTrack.entity.UserEntity;
import ru.kpfu.itis.lifeTrack.entity.WorkflowEntity;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.exception.Workflow.WorkflowNotFoundException;
import ru.kpfu.itis.lifeTrack.model.Workflow;

public interface WorkflowService {

    Workflow getWorkflow(Long id) throws WorkflowNotFoundException;

    Workflow insertWorkflow(Long userId, WorkflowEntity workflowEntity) throws UserNotFoundException;

    Workflow patchWorkflow(Long id, JsonPatch jsonPatch);

    Workflow updateWorkflow(Long id, UserEntity updatedWorkflowEntity);

    Long deleteWorkflow(Long id);
}
