package ru.kpfu.itis.lifeTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lifeTrack.model.RoleEntity;
import ru.kpfu.itis.lifeTrack.model.UserEntity;
import ru.kpfu.itis.lifeTrack.model.WorkflowEntity;

import java.util.Optional;
import java.util.Set;

public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    Set<RoleEntity> findAllByUserId(Long userId);
    Set<RoleEntity> findAllByWorkflowId(Long workflowId);
    Optional<RoleEntity> findByUserIdAndWorkflowId(Long userId, Long workflowId);

    void deleteAllByWorkflow(WorkflowEntity workflowEntity);
    void deleteAllByUser(UserEntity userEntity);
}
