package ru.kpfu.itis.lifeTrack.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.lifeTrack.dto.response.WorkflowDto;
import ru.kpfu.itis.lifeTrack.model.WorkflowEntity;

@Mapper(componentModel = "spring")
public interface WorkflowMapper {

    WorkflowEntity dtoToEntity(WorkflowDto workflowDto);

    WorkflowDto entityToDto(WorkflowEntity workflowEntity);

}
