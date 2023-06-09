package ru.kpfu.itis.lifeTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lifeTrack.model.EventEntity;
import ru.kpfu.itis.lifeTrack.model.ProjectEntity;

import java.util.Optional;
import java.util.Set;

public interface EventRepo extends JpaRepository<EventEntity, Long> {

    Set<EventEntity> findAllByProject(ProjectEntity project);

    Optional<EventEntity> findByProjectAndId(ProjectEntity project, Long id);
}
