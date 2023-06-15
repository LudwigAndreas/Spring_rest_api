package ru.kpfu.itis.lifeTrack.model.Workflow;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "workflow_role_name")
@Hidden
public class WorkflowRole {
    @Id
    private Long id;

    @Column(name = "name", length = 32, nullable = false, unique = true)
    private String name;
}
