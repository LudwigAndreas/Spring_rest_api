package ru.kpfu.itis.lifeTrack.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lifeTrack.dto.request.EventRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.EventResponseDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;
import ru.kpfu.itis.lifeTrack.mapper.EventMapper;
import ru.kpfu.itis.lifeTrack.repository.EventRepo;
import ru.kpfu.itis.lifeTrack.service.EventService;

import java.util.Set;


@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventRepo eventRepo;

    @Override
    public Set<EventResponseDto> getEventList(Long userId, Long workflowId, Long projectId) throws NotFoundException {
        return null;
    }

    @Override
    public EventResponseDto getEvent(Long userId, Long workflowId, Long projectId, Long eventId) throws NotFoundException {
        return null;
    }

    @Override
    public EventResponseDto insertEvent(Long userId, Long workflowId, Long projectId, EventRequestDto requestDto) throws NotFoundException {
        return null;
    }

    @Override
    public EventResponseDto moveEvent(Long userId, Long workflowId, Long projectId, Long eventId, String destination) throws NotFoundException {
        return null;
    }

    @Override
    public EventResponseDto patchEvent(Long userId, Long workflowId, Long projectId, Long eventId, JsonPatch patch) throws NotFoundException, JsonProcessingException, JsonPatchException {
        return null;
    }

    @Override
    public EventResponseDto updateEvent(Long userId, Long workflowId, Long projectId, Long eventId, EventRequestDto requestDto) throws NotFoundException {
        return null;
    }

    @Override
    public Long deleteEvent(Long userId, Long workflowId, Long projectId, Long eventId) throws NotFoundException {
        return null;
    }
}
