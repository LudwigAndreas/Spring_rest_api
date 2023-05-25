package ru.kpfu.itis.lifeTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lifeTrack.entity.ProjectEntity;

public interface ProjectRepo extends JpaRepository<ProjectEntity, Long> {
}
