package ru.kpfu.itis.lifeTrack.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.lifeTrack.dto.request.EventRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.EventResponseDto;
import ru.kpfu.itis.lifeTrack.model.EventEntity;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventEntity responseDtoToEntity(EventResponseDto responseDto);

    EventEntity requestDtoToEntity(EventRequestDto requestDto);

    EventResponseDto requestDtoToResponseDto(EventRequestDto requestDto);

    EventRequestDto responseDtoToRequestDto(EventResponseDto responseDto);

    EventResponseDto entityToResponseDto(EventEntity eventEntity);

    EventRequestDto entityToRequestDto(EventEntity eventEntity);

    Set<EventEntity> responseDtoSetToEntitySet(Set<EventResponseDto> dtoSet);

    Set<EventResponseDto> entitySetToResponseDtoSet(Set<EventEntity> entitySet);
}
