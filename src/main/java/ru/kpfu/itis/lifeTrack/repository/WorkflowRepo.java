package ru.kpfu.itis.lifeTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lifeTrack.entity.WorkflowEntity;


public interface WorkflowRepo extends JpaRepository<WorkflowEntity, Long> {
}
