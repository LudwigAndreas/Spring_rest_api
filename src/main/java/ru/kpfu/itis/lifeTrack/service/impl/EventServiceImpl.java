package ru.kpfu.itis.lifeTrack.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lifeTrack.dto.request.EventRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.EventResponseDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.exception.user.UserNotFoundException;
import ru.kpfu.itis.lifeTrack.mapper.EventMapper;
import ru.kpfu.itis.lifeTrack.model.EventEntity;
import ru.kpfu.itis.lifeTrack.model.ProjectEntity;
import ru.kpfu.itis.lifeTrack.model.user.UserEntity;
import ru.kpfu.itis.lifeTrack.repository.EventRepo;
import ru.kpfu.itis.lifeTrack.repository.ProjectRepo;
import ru.kpfu.itis.lifeTrack.repository.UserRepo;
import ru.kpfu.itis.lifeTrack.service.EventService;

import java.util.Set;


@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final ProjectRepo projectRepo;
    private final EventRepo eventRepo;
    private final UserRepo userRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Set<EventResponseDto> getEventList(String userId, Long workflowId, Long projectId) throws NotFoundException {
        ProjectEntity projectEntity = projectRepo.findByWorkflowIdAndId(workflowId, projectId).orElseThrow(() -> new NotFoundException("Relation between workflow and project was not found"));
        Set<EventEntity> eventSet = eventRepo.findAllByProject(projectEntity);
        return eventMapper.entitySetToResponseDtoSet(eventSet);
    }

    @Override
    public EventResponseDto getEvent(String userId, Long workflowId, Long projectId, Long eventId) throws NotFoundException {
        ProjectEntity projectEntity = projectRepo.findByWorkflowIdAndId(workflowId, projectId).orElseThrow(() -> new NotFoundException("Relation between workflow and project was not found"));
        EventEntity event = eventRepo.findByProjectAndId(projectEntity, eventId).orElseThrow(() -> new NotFoundException("Event was not found"));
        return eventMapper.entityToResponseDto(event);
    }

    @Override
    public EventResponseDto insertEvent(String userId, Long workflowId, Long projectId, EventRequestDto requestDto) throws NotFoundException {
        ProjectEntity projectEntity = projectRepo.findByWorkflowIdAndId(workflowId, projectId).orElseThrow(() -> new NotFoundException("Relation between workflow and project was not found"));
        EventEntity eventEntity = eventMapper.requestDtoToEntity(requestDto);
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User was not found"));
        eventEntity.setCreator(user);
        eventEntity.setProject(projectEntity);
        EventEntity event = eventRepo.save(eventEntity);
        return eventMapper.entityToResponseDto(event);
    }

    @Override
    public EventResponseDto moveEvent(String userId, Long workflowId, Long projectId, Long eventId, String destination) throws NotFoundException {
        return null;
    }

    @Override
    public EventResponseDto patchEvent(String userId, Long workflowId, Long projectId, Long eventId, JsonPatch patch) throws NotFoundException, JsonProcessingException, JsonPatchException {
        ProjectEntity projectEntity = projectRepo.findByWorkflowIdAndId(workflowId, projectId).orElseThrow(() -> new NotFoundException("Relation between workflow and project was not found"));
        EventEntity eventEntity = eventRepo.findByProjectAndId(projectEntity, eventId).orElseThrow(() -> new NotFoundException("Event was not found"));

        JsonNode patched = patch.apply(objectMapper.convertValue(eventEntity, JsonNode.class));
        return eventMapper.entityToResponseDto(eventRepo.save(objectMapper.treeToValue(patched, EventEntity.class)));

    }

    @Override
    public EventResponseDto updateEvent(String userId, Long workflowId, Long projectId, Long eventId, EventRequestDto requestDto) throws NotFoundException {
        ProjectEntity projectEntity = projectRepo.findByWorkflowIdAndId(workflowId, projectId).orElseThrow(() -> new NotFoundException("Relation between workflow and project was not found"));
        EventEntity origin = eventRepo.findByProjectAndId(projectEntity, eventId).orElseThrow(() -> new NotFoundException("Event was not found"));
        EventEntity updated = eventMapper.requestDtoToEntity(requestDto);
        updated.setProject(projectEntity);
        updated.setId(eventId);
        updated.setCreator(origin.getCreator());
        updated.setCreated(origin.getCreated());
        updated.setUpdated(origin.getUpdated());
        return eventMapper.entityToResponseDto(eventRepo.save(updated));
    }

    @Override
    public Long deleteEvent(String userId, Long workflowId, Long projectId, Long eventId) throws NotFoundException {
        ProjectEntity projectEntity = projectRepo.findByWorkflowIdAndId(workflowId, projectId).orElseThrow(() -> new NotFoundException("Relation between workflow and project was not found"));
        EventEntity eventEntity = eventRepo.findByProjectAndId(projectEntity, eventId).orElseThrow(() -> new NotFoundException("Event was not found"));
        eventRepo.delete(eventEntity);
        return eventId;
    }
}
