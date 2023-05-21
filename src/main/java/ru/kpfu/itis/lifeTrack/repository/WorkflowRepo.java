package ru.kpfu.itis.lifeTrack.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.lifeTrack.entity.WorkflowEntity;

import java.util.Optional;

public interface WorkflowRepo extends CrudRepository<WorkflowEntity, Long> {
}
