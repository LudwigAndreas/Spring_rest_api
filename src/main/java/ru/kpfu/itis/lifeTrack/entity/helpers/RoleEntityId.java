package ru.kpfu.itis.lifeTrack.entity.helpers;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntityId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "workflow_id")
    private Long workflowId;
}
