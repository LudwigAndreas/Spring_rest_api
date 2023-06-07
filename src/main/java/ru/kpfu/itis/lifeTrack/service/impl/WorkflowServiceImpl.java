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
import ru.kpfu.itis.lifeTrack.dto.response.ProjectResponseDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.dto.response.WorkflowDto;
import ru.kpfu.itis.lifeTrack.exception.Workflow.WorkflowNotFoundException;
import ru.kpfu.itis.lifeTrack.mapper.WorkflowMapper;
import ru.kpfu.itis.lifeTrack.model.RoleEntity;
import ru.kpfu.itis.lifeTrack.model.UserEntity;
import ru.kpfu.itis.lifeTrack.model.WorkflowEntity;
import ru.kpfu.itis.lifeTrack.model.helpers.AccessRole;
import ru.kpfu.itis.lifeTrack.repository.ProjectRepo;
import ru.kpfu.itis.lifeTrack.repository.RoleRepo;
import ru.kpfu.itis.lifeTrack.repository.UserRepo;
import ru.kpfu.itis.lifeTrack.repository.WorkflowRepo;
import ru.kpfu.itis.lifeTrack.service.WorkflowService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepo workflowRepo;
    private final WorkflowMapper workflowMapper;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Set<WorkflowDto> getWorkflowList(Long userId) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        if (userEntity.isEmpty()) {
            log.warn("IN getWorkflowList: User with {} id was not found", userId);
            throw new UserNotFoundException("User with this id does not exists");
        }
//          TODO add check that role >= reader
        Set<RoleEntity> roleEntitySet = roleRepo.findAllByUserId(userId);
        Set<WorkflowDto> workflowSet = new HashSet<>();
        for (RoleEntity roleEntity : roleEntitySet) {
            workflowSet.add(workflowMapper.entityToDto(roleEntity.getWorkflow()));
        }

        return workflowSet;
    }

    @Override
    public WorkflowDto getWorkflow(Long userId, Long id) throws NotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        if (userEntity.isEmpty()) {
            log.warn("IN getWorkflow: User with {} id was not found", userId);
            throw new UserNotFoundException("User with this id does not exists");
        }

        RoleEntity roleEntity = roleRepo.findByUserIdAndWorkflowId(userId, id)
                .orElseThrow(() -> new WorkflowNotFoundException("Workflow with this id does not exists"));
//          TODO add checking that role >= reader
        WorkflowDto workflowDto = workflowMapper.entityToDto(roleEntity.getWorkflow());

        return workflowDto;
    }

    @Override
    public WorkflowDto insertWorkflow(Long userId, WorkflowDto request) throws NotFoundException {
        Optional<UserEntity> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            log.warn("IN insertWorkflow: User with {} id was not found", userId);
            throw new UserNotFoundException("User with this id does not exists");
        }
        WorkflowEntity saved = workflowRepo.save(workflowMapper.dtoToEntity(request));
        log.warn("Role connection between: User:" +  optionalUser.get().getId() + " workflow:" + saved.getId() + " role:" + AccessRole.owner);
        WorkflowDto response = workflowMapper.entityToDto(roleRepo.save(new RoleEntity(optionalUser.get(), saved, AccessRole.owner)).getWorkflow());
        return response;
    }

    @Override
    public WorkflowDto patchWorkflow(Long userId, Long id, JsonPatch jsonPatch) throws NotFoundException, JsonProcessingException, JsonPatchException {
        Optional<UserEntity> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            log.warn("IN patchWorkflow: User with {} id was not found", userId);
            throw new UserNotFoundException("User with this id does not exists");
        }
        RoleEntity roleEntity = roleRepo.findByUserIdAndWorkflowId(userId, id)
                .orElseThrow(() -> new WorkflowNotFoundException("Workflow with this id does not exists"));
        WorkflowEntity workflowEntity = roleEntity.getWorkflow();
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(workflowEntity, JsonNode.class));

        return workflowMapper.entityToDto(workflowRepo.save(objectMapper.treeToValue(patched, WorkflowEntity.class)));
    }

    @Override
    public WorkflowDto updateWorkflow(Long userId, Long id, WorkflowDto request) throws NotFoundException {
        RoleEntity roleEntity = roleRepo.findByUserIdAndWorkflowId(userId, id)
                .orElseThrow(() -> new NotFoundException("Relation between this user and this workflow does not exists"));

        WorkflowEntity updated = workflowMapper.dtoToEntity(request);
        updated.setId(id);
        return workflowMapper.entityToDto(workflowRepo.save(updated));
    }

    @Override
    @Transactional
    public Long deleteWorkflow(Long userId, Long id) throws NotFoundException {
        RoleEntity roleEntity = roleRepo.findByUserIdAndWorkflowId(userId, id)
                .orElseThrow(() -> new NotFoundException("Relation between this user and this workflow does not exists"));
        WorkflowEntity toDelete = roleEntity.getWorkflow();
        log.info("IN deleteWorkflow workflow with id {} was deleted", toDelete.getId());

        roleRepo.deleteAllByWorkflow(toDelete);
        projectRepo.deleteAllByWorkflow(toDelete);
        workflowRepo.delete(toDelete);
        return id;
    }
}
