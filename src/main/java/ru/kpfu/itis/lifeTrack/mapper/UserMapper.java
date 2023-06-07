package ru.kpfu.itis.lifeTrack.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.lifeTrack.model.UserEntity;
import ru.kpfu.itis.lifeTrack.dto.response.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity dtoToEntity(UserDto userDto);

    UserDto entityToDto(UserEntity userEntity);

}
