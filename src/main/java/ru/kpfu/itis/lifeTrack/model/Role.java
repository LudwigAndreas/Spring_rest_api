package ru.kpfu.itis.lifeTrack.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.kpfu.itis.lifeTrack.entity.RoleEntity;
import ru.kpfu.itis.lifeTrack.entity.helpers.AccessRole;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
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
        if (roleEntitySet != null)
            return roleEntitySet.stream().map(Role::toModel).collect(Collectors.toSet());
        else
            return null;
    }
}
