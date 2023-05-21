package ru.kpfu.itis.lifeTrack.model;

import lombok.*;
import ru.kpfu.itis.lifeTrack.entity.RoleEntity;
import ru.kpfu.itis.lifeTrack.entity.UserEntity;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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
