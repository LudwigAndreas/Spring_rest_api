package ru.kpfu.itis.lifeTrack.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.kpfu.itis.lifeTrack.entity.UserEntity;
import ru.kpfu.itis.lifeTrack.model.Role;

import java.util.Set;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Date createdDate;
    private Date lastUpdatedDate;
    private Set<Role> workflows;

    static public User toModel(UserEntity userEntity) {
        log.debug("UserEntity was converted to model: " + userEntity.getWorkflows());
        return new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getFirstname(),
                userEntity.getLastname(),
                userEntity.getEmail(),
                userEntity.getCreatedDate(),
                userEntity.getLastUpdatedDate(),
                Role.toModel(userEntity.getWorkflows())
        );
    }
}
