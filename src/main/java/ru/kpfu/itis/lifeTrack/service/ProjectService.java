package ru.kpfu.itis.lifeTrack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ru.kpfu.itis.lifeTrack.dto.request.ProjectRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.ProjectResponseDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;

import java.util.Set;

public interface ProjectService {
    Set<ProjectResponseDto> getProjectList(Long userId) throws NotFoundException;

    Set<ProjectResponseDto> getProjectList(Long userId, Long workflowId) throws NotFoundException;

    ProjectResponseDto getProject(Long userId, Long workflowId, Long projectId) throws NotFoundException;

    ProjectResponseDto insertProject(Long userId, Long workflowId, ProjectRequestDto projectDto) throws NotFoundException;

    ProjectResponseDto patchProject(Long userId, Long workflowId, Long projectId, JsonPatch patch) throws NotFoundException, JsonPatchException, JsonProcessingException;

    ProjectResponseDto updateProject(Long userId, Long workflowId, Long projectId, ProjectRequestDto update) throws NotFoundException;

    Long deleteProject(Long userId, Long workflowId, Long projectId) throws NotFoundException;

}
