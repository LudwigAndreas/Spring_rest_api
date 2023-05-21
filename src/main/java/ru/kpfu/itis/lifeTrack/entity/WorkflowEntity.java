package ru.kpfu.itis.lifeTrack.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "workflow")
public class WorkflowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gcal_id")
    private String gCalId;
    private String summary;
    private String description;
    private String timezone;
    private String color;

    @OneToMany(mappedBy = "workflow")
    private Set<RoleEntity> authorized;

}
