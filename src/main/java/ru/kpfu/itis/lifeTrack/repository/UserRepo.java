package ru.kpfu.itis.lifeTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.lifeTrack.model.UserEntity;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional <UserEntity> findUserEntityByUsername(String username);
    Optional <UserEntity> findUserEntityByEmail(String email);
    Optional <UserEntity> findUserEntityByUsernameAndEmail(String username, String email);
}
//    The bean 'userRepo', defined in ru.kpfu.itis.lifeTrack.repository.UserRepo
//    defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration,
//    could not be registered. A bean with that name has already been defined in
//    ru.kpfu.itis.lifeTrack.repository.UserRepo defined in @EnableJdbcRepositories declared
//    on JdbcRepositoriesRegistrar.EnableJdbcRepositoriesConfiguration and overriding is disabled.
