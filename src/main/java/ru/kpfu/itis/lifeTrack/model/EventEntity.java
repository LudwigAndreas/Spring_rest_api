package ru.kpfu.itis.lifeTrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long project_id;

    @Column(name = "gcal_event_id", length = 32)
    private String googleEventId;

    @Column(name = "ical_uid", length = 26)
    private String iCalendarUID;

    @Column(name = "summary", length = 254)
    private String summary;

    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    @CreatedDate
    private Timestamp created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    @LastModifiedDate
    private Timestamp updated;

    @Column(name = "creator", nullable = false)
    private Long creator;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_start")
    private Timestamp planStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_end")
    private Timestamp planEnd;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_start")
    private Timestamp userStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_end")
    private Timestamp userEnd;

    @Column(name = "finished", nullable = false)
    private Boolean finished;

    @Column(name = "recurrence")
    private String recurrence;

    @Column(name = "recurring_event_id")
    private Long recurringEventId;

    @Column(name = "color")
    private String color;
}
