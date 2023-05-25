package ru.kpfu.itis.lifeTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lifeTrack.entity.EventEntity;

public interface EventRepo extends JpaRepository<EventEntity, Long> {

}
