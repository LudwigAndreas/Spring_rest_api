package ru.kpfu.itis.lifeTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lifeTrack.entity.RoleEntity;

import java.util.Set;

public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    Set<RoleEntity> findAllByUserId(Long userId);
    Set<RoleEntity> findAllByWorkflowId(Long workflowId);
}
