package ru.kpfu.itis.lifeTrack.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.lifeTrack.entity.UserEntity;
import ru.kpfu.itis.lifeTrack.model.User;

@Mapper
public interface UserMapper {
    UserEntity toUserEntity(User user);

    User toUserModel(UserEntity userEntity);
}
