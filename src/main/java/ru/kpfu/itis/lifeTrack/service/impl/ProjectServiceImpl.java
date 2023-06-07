package ru.kpfu.itis.lifeTrack.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lifeTrack.dto.request.ProjectRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.ProjectResponseDto;
import ru.kpfu.itis.lifeTrack.dto.response.WorkflowDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.mapper.ProjectMapper;
import ru.kpfu.itis.lifeTrack.model.ProjectEntity;
import ru.kpfu.itis.lifeTrack.model.RoleEntity;
import ru.kpfu.itis.lifeTrack.model.WorkflowEntity;
import ru.kpfu.itis.lifeTrack.repository.ProjectRepo;
import ru.kpfu.itis.lifeTrack.repository.RoleRepo;
import ru.kpfu.itis.lifeTrack.service.ProjectService;
import ru.kpfu.itis.lifeTrack.service.UserManagementService;
import ru.kpfu.itis.lifeTrack.service.WorkflowService;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectRepo projectRepo;
    private final UserManagementService userManagementService;
    private final WorkflowService workflowService;
    private final RoleRepo roleRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Set<ProjectResponseDto> getProjectList(Long userId) throws NotFoundException {
        userManagementService.isUserExists(userId);
        Set<WorkflowDto> workflowSet = workflowService.getWorkflowList(userId);
        Set<ProjectResponseDto> projectDtoSet = new HashSet<>();
        for (WorkflowDto workflow : workflowSet) {
            projectDtoSet.addAll(projectMapper.entitySetToResponseDtoSet(projectRepo.findAllByWorkflowId(workflow.getId())));
        }
        return projectDtoSet;
    }

    @Override
    public Set<ProjectResponseDto> getProjectList(Long userId, Long workflowId) throws NotFoundException {
        userManagementService.isUserExists(userId);
        return new HashSet<>(projectMapper.entitySetToResponseDtoSet(projectRepo.findAllByWorkflowId(workflowId)));
    }

    @Override
    public ProjectResponseDto getProject(Long userId, Long workflowId, Long projectId) throws NotFoundException {
        userManagementService.isUserExists(userId);
        return projectMapper.entityToResponseDto(projectRepo.findByWorkflowIdAndId(workflowId, projectId).orElseThrow(() -> new NotFoundException("Relation between this workflow and this project does not exists")));
    }

    @Override
    public ProjectResponseDto insertProject(Long userId, Long workflowId, ProjectRequestDto projectDto) throws NotFoundException {
        ProjectEntity projectEntity = projectMapper.requestDtoToEntity(projectDto);
        WorkflowEntity workflow = roleRepo.findByUserIdAndWorkflowId(userId, workflowId).orElseThrow(() -> new NotFoundException("Relation between User and workflow was not found")).getWorkflow();
        projectEntity.setWorkflow(workflow);
        return projectMapper.entityToResponseDto(projectRepo.save(projectEntity));
    }

    @Override
    public ProjectResponseDto patchProject(Long userId, Long workflowId, Long projectId, JsonPatch patch) throws NotFoundException, JsonPatchException, JsonProcessingException {
        userManagementService.isUserExists(userId);
        ProjectEntity projectEntity = projectRepo.findByWorkflowIdAndId(workflowId, projectId).orElseThrow(() -> new NotFoundException("Relation between workflow and project not found"));
        JsonNode patched = patch.apply(objectMapper.convertValue(projectEntity, JsonNode.class));
        return projectMapper.entityToResponseDto(projectRepo.save(objectMapper.treeToValue(patched, ProjectEntity.class)));
    }

    @Override
    @Transactional
    public ProjectResponseDto updateProject(Long userId, Long workflowId, Long projectId, ProjectRequestDto update) throws NotFoundException {
        userManagementService.isUserExists(userId);
        ProjectEntity updated = projectMapper.requestDtoToEntity(update);
        updated.setId(projectId);
        RoleEntity roleEntity = roleRepo.findByUserIdAndWorkflowId(userId, workflowId).orElseThrow(() -> new NotFoundException("Relation between Workflow and repo was not found"));
        updated.setWorkflow(roleEntity.getWorkflow());
        return projectMapper.entityToResponseDto(projectRepo.save(updated));
    }

    @Override
    public Long deleteProject(Long userId, Long workflowId, Long projectId) throws NotFoundException {
        ProjectEntity projectEntity = projectRepo.findByWorkflowIdAndId(workflowId, projectId).orElseThrow(() -> new NotFoundException("Relation between workflow and project not found"));
        projectRepo.delete(projectEntity);
        return projectId;
    }
}
