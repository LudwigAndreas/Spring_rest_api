package ru.kpfu.itis.lifeTrack.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.lifeTrack.dto.response.WorkflowDto;
import ru.kpfu.itis.lifeTrack.model.WorkflowEntity;

@Mapper(componentModel = "spring")
public interface WorkflowMapper {
    WorkflowEntity toEntity(WorkflowDto workflowDto);
    WorkflowDto toDto(WorkflowEntity workflowEntity);
}
