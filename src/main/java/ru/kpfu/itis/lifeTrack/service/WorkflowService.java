package ru.kpfu.itis.lifeTrack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.dto.response.WorkflowDto;

import java.util.Set;

public interface WorkflowService {

    Set<WorkflowDto> getWorkflowList(Long userId) throws UserNotFoundException;

    WorkflowDto getWorkflow(Long userId, Long id) throws NotFoundException;

    WorkflowDto insertWorkflow(Long userId, WorkflowDto request) throws NotFoundException;

    WorkflowDto patchWorkflow(Long userId, Long id, JsonPatch jsonPatch) throws NotFoundException, JsonProcessingException, JsonPatchException;

    WorkflowDto updateWorkflow(Long userId, Long id, WorkflowDto request) throws NotFoundException;

    Long deleteWorkflow(Long userId, Long id) throws NotFoundException;
}
