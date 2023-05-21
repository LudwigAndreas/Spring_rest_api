package ru.kpfu.itis.lifeTrack.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.lifeTrack.entity.UserEntity;

public interface RoleRepo extends CrudRepository<UserEntity, Long> {

}
