package ru.kpfu.itis.lifeTrack.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_statistics")
public class ProjectStatisticEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "time", nullable = false)
    private Time time;

}
