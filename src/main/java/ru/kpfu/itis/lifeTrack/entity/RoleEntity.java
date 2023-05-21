package ru.kpfu.itis.lifeTrack.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kpfu.itis.lifeTrack.entity.helpers.AccessRole;
import ru.kpfu.itis.lifeTrack.entity.helpers.RoleEntityId;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "workflow_user_access_role")
public class RoleEntity {

    @EmbeddedId
    private RoleEntityId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("workflowId")
    @JoinColumn(name = "workflow_id")
    private WorkflowEntity workflow;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private AccessRole role;

}
