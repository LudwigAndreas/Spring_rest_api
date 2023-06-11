package ru.kpfu.itis.lifeTrack.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponseDto {
    private Long id;
    private Long projectId;
    private String googleEventId;
    private String iCalendarUID;
    private String summary;
    private String description;
    private Timestamp created;
    private Timestamp updated;
    private String creator;
    private Timestamp planStart;
    private Timestamp planEnd;
    private Timestamp userStart;
    private Timestamp userEnd;
    private Boolean finished;
    private String[] recurrence;
    private Long recurringEventId;
    private String color;
}
