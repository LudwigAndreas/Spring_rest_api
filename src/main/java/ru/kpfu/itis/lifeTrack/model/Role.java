package ru.kpfu.itis.lifeTrack.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.kpfu.itis.lifeTrack.entity.RoleEntity;
import ru.kpfu.itis.lifeTrack.entity.helpers.AccessRole;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Role {
    private Long userId;
    private Long workflowId;
    private AccessRole role;

    static public Role toModel(RoleEntity roleEntity) {
        return new Role(
                roleEntity.getUser().getId(),
                roleEntity.getWorkflow().getId(),
                roleEntity.getRole()
        );
    }

    static public Set<Role> toModel(Set<RoleEntity> roleEntitySet) {
        log.debug("RoleEntitySet was converted to model");
        if (!roleEntitySet.isEmpty()) {
            log.info("RoleEntitySet is not empty");
            return roleEntitySet.stream().map(Role::toModel).collect(Collectors.toSet());
        } else {
            log.info("RoleEntitySet is empty");
            return null;
        }
    }
}
