package ru.kpfu.itis.lifeTrack.model.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ru.kpfu.itis.lifeTrack.model.Workflow.WorkflowAccessRoleEntity;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", length = 32, nullable = false, unique = true)
    private String username;

    @Column(name = "firstname", length = 32)
    private String firstname;

    @Column(name = "lastname", length = 32)
    private String lastname;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    private String email;

    @Column(name = "userpic")
    private String userPicture;

    @Column(name = "password", length = 32, nullable = false)
    private String password;

    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;

    @Column(name = "last_updated_date")
    @LastModifiedDate
    private Date lastUpdatedDate;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<WorkflowAccessRoleEntity> workflows = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private List<RefreshTokenEntity> tokens;

}
