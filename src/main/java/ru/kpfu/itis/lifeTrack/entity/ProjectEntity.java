package ru.kpfu.itis.lifeTrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "workflow_id", nullable = false)
    private Long workflowId;
    @Column(name = "summary", length = 254)
    private String summary;
    @Column(name = "description", length = 200)
    private String description;
    @Column(name = "color", length = 7)
    private String color;
}
