package ru.kpfu.itis.lifeTrack.model.Workflow;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowRoleEntityId implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "workflow_id")
    private Long workflowId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }
}
