package ru.kpfu.itis.lifeTrack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ru.kpfu.itis.lifeTrack.dto.request.EventRequestDto;
import ru.kpfu.itis.lifeTrack.dto.response.EventResponseDto;
import ru.kpfu.itis.lifeTrack.exception.NotFoundException;

import java.util.Set;

public interface EventService {

    Set<EventResponseDto> getEventList(Long userId, Long workflowId, Long projectId) throws NotFoundException;

    EventResponseDto getEvent(Long userId, Long workflowId, Long projectId, Long eventId) throws NotFoundException;

    EventResponseDto insertEvent(Long userId, Long workflowId, Long projectId, EventRequestDto requestDto) throws NotFoundException;

    EventResponseDto moveEvent(Long userId, Long workflowId, Long projectId, Long eventId, String destination) throws NotFoundException;

    EventResponseDto patchEvent(Long userId, Long workflowId, Long projectId, Long eventId, JsonPatch patch) throws NotFoundException, JsonProcessingException, JsonPatchException;

    EventResponseDto updateEvent(Long userId, Long workflowId, Long projectId, Long eventId, EventRequestDto requestDto) throws NotFoundException;

    Long deleteEvent(Long userId, Long workflowId, Long projectId, Long eventId) throws NotFoundException;
}
