package ru.kpfu.itis.lifeTrack.model.helpers;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntityId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "workflow_id")
    private Long workflowId;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }
}
