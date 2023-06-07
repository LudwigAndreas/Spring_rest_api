package ru.kpfu.itis.lifeTrack.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.exception.User.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.model.RoleEntity;
import ru.kpfu.itis.lifeTrack.model.UserEntity;
import ru.kpfu.itis.lifeTrack.model.helpers.AccessRole;
import ru.kpfu.itis.lifeTrack.repository.RoleRepo;
import ru.kpfu.itis.lifeTrack.repository.UserRepo;
import ru.kpfu.itis.lifeTrack.repository.WorkflowRepo;
import ru.kpfu.itis.lifeTrack.service.UserManagementService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepo userRepo;
    private final WorkflowRepo workflowRepo;
    private final RoleRepo roleRepo;

    @Override
    public boolean isUserExists(Long userId){
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        return userEntity.isPresent();
    }

    @Override
    public AccessRole getUserRole(Long userId, Long workflowId) throws NotFoundException {
        Optional<RoleEntity> role = roleRepo.findByUserIdAndWorkflowId(userId, workflowId);
        if (role.isEmpty())
            throw new NotFoundException("Relation between this user and this workflow does not exists");
        return role.get().getRole();
    }
}
