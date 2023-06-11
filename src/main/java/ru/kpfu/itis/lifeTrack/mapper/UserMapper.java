package ru.kpfu.itis.lifeTrack.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.lifeTrack.model.user.UserEntity;
import ru.kpfu.itis.lifeTrack.dto.response.UserDto;
import ru.kpfu.itis.lifeTrack.security.jwt.SecurityUserDetails;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity stringToEntity(String id);

    UserEntity dtoToEntity(UserDto userDto);

    UserDto entityToDto(UserEntity userEntity);

    SecurityUserDetails entityToDetails(UserEntity userEntity);

    UserEntity detailsToEntity(SecurityUserDetails securityUserDetails);
}
