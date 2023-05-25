package ru.kpfu.itis.lifeTrack.service.impl;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kpfu.itis.lifeTrack.entity.RoleEntity;
import ru.kpfu.itis.lifeTrack.entity.UserEntity;
import ru.kpfu.itis.lifeTrack.entity.WorkflowEntity;
import ru.kpfu.itis.lifeTrack.entity.helpers.AccessRole;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.exception.Workflow.WorkflowNotFoundException;
import ru.kpfu.itis.lifeTrack.model.Workflow;
import ru.kpfu.itis.lifeTrack.repository.RoleRepo;
import ru.kpfu.itis.lifeTrack.repository.UserRepo;
import ru.kpfu.itis.lifeTrack.repository.WorkflowRepo;
import ru.kpfu.itis.lifeTrack.service.WorkflowService;

import java.util.Optional;

@Service
@Slf4j
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepo workflowRepo;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    @Autowired
    public WorkflowServiceImpl(WorkflowRepo workflowRepo, RoleRepo roleRepo, UserRepo userRepo) {
        this.workflowRepo = workflowRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

    public Workflow getWorkflow(@PathVariable Long id) throws WorkflowNotFoundException {
        Optional<WorkflowEntity> workflowEntity = workflowRepo.findById(id);
        if (workflowEntity.isEmpty()) {
            throw new WorkflowNotFoundException("Workflow with this id does not exist");
        }
        return Workflow.toModel(workflowEntity.get());
    }

    public Workflow insertWorkflow(Long userId, WorkflowEntity workflowEntity) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty())
            throw new UserNotFoundException("Provided user not found");
        WorkflowEntity saved = workflowRepo.save(workflowEntity);
        log.warn("Role connection between: User:" +  optionalUser.get().getId() + " workflow:" + saved.getId() + " role:" + AccessRole.owner);
        roleRepo.save(new RoleEntity(optionalUser.get(), saved, AccessRole.owner));
        return Workflow.toModel(saved);
    }

    @Override
    public Workflow patchWorkflow(Long id, JsonPatch jsonPatch) {
        return null;
    }

    @Override
    public Workflow updateWorkflow(Long id, UserEntity updatedWorkflowEntity) {
        return null;
    }

    @Override
    public Long deleteWorkflow(Long id) {
        return null;
    }
}
