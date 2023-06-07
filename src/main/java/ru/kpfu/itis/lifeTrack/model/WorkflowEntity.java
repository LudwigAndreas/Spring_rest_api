package ru.kpfu.itis.lifeTrack.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "workflow")
public class WorkflowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gcal_id", length = 254)
    private String gCalId;

    @Column(name = "summary", length = 254)
    private String summary;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "timezone", length = 64)
    private String timezone;

    @Column(name = "color", length = 7)
    private String color;

    @OneToMany(mappedBy = "workflow", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<RoleEntity> authorized;

    @OneToMany(mappedBy = "workflow", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ProjectEntity> projects;

}
