package ru.kpfu.itis.lifeTrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
@Builder
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private WorkflowEntity workflow;

    @Column(name = "summary", length = 254)
    private String summary;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "color", length = 7)
    private String color;

}
