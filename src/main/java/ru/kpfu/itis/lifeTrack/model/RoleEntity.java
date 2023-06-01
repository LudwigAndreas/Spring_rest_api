package ru.kpfu.itis.lifeTrack.model;

import jakarta.persistence.*;
import lombok.*;
import ru.kpfu.itis.lifeTrack.model.helpers.AccessRole;
import ru.kpfu.itis.lifeTrack.model.helpers.RoleEntityId;

@Entity
@EqualsAndHashCode
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

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AccessRole role;

    public RoleEntity(UserEntity userEntity, WorkflowEntity workflowEntity, AccessRole role) {
        this.id = new RoleEntityId(userEntity.getId(), workflowEntity.getId());
        this.user = userEntity;
        this.workflow = workflowEntity;
        this.role = role;
    }

    public RoleEntity() {
        this.id = new RoleEntityId();
    }

    public RoleEntityId getId() {
        return id;
    }

    public void setId(RoleEntityId id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.id.setUserId(user.getId());
        this.user = user;
    }

    public WorkflowEntity getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowEntity workflow) {
        this.id.setWorkflowId(workflow.getId());
        this.workflow = workflow;
    }

    public AccessRole getRole() {
        return role;
    }

    public void setRole(AccessRole role) {
        this.role = role;
    }
}
