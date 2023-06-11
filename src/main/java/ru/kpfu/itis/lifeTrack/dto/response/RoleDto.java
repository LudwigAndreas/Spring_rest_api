package ru.kpfu.itis.lifeTrack.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.kpfu.itis.lifeTrack.model.Workflow.WorkflowAccessRoleEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {
    private String userId;
    private Long workflowId;
    private String role;

    static public RoleDto toModel(WorkflowAccessRoleEntity workflowAccessRoleEntity) {
        return new RoleDto(
                workflowAccessRoleEntity.getUser().getId(),
                workflowAccessRoleEntity.getWorkflow().getId(),
                workflowAccessRoleEntity.getRole().getName()
        );
    }

    static public Set<RoleDto> toModel(Set<WorkflowAccessRoleEntity> workflowAccessRoleEntitySet) {
        log.debug("RoleEntitySet was converted to model");
        if (!workflowAccessRoleEntitySet.isEmpty()) {
            log.info("RoleEntitySet is not empty");
            return workflowAccessRoleEntitySet.stream().map(RoleDto::toModel).collect(Collectors.toSet());
        } else {
            log.info("RoleEntitySet is empty");
            return null;
        }
    }
}
