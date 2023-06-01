package ru.kpfu.itis.lifeTrack.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.kpfu.itis.lifeTrack.model.RoleEntity;
import ru.kpfu.itis.lifeTrack.model.helpers.AccessRole;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {
    private Long userId;
    private Long workflowId;
    private AccessRole role;

    static public RoleDto toModel(RoleEntity roleEntity) {
        return new RoleDto(
                roleEntity.getUser().getId(),
                roleEntity.getWorkflow().getId(),
                roleEntity.getRole()
        );
    }

    static public Set<RoleDto> toModel(Set<RoleEntity> roleEntitySet) {
        log.debug("RoleEntitySet was converted to model");
        if (!roleEntitySet.isEmpty()) {
            log.info("RoleEntitySet is not empty");
            return roleEntitySet.stream().map(RoleDto::toModel).collect(Collectors.toSet());
        } else {
            log.info("RoleEntitySet is empty");
            return null;
        }
    }
}
