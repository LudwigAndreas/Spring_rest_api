package ru.kpfu.itis.lifeTrack.model;

import lombok.*;
import ru.kpfu.itis.lifeTrack.entity.WorkflowEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workflow {
    private Long id;
    private String summary;
    private String description;
    private String timeZone;
    private String color;

    static public Workflow toModel(WorkflowEntity workflowEntity) {
        return new Workflow(
                workflowEntity.getId(),
                workflowEntity.getSummary(),
                workflowEntity.getDescription(),
                workflowEntity.getTimezone(),
                workflowEntity.getColor()
        );
    }
}
