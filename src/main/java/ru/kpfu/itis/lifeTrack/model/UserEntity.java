package ru.kpfu.itis.lifeTrack.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 32, nullable = false, unique = true)
    private String username;

    @Column(name = "firstname", length = 32)
    private String firstname;

    @Column(name = "lastname", length = 32)
    private String lastname;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 32, nullable = false)
    private String password;

    @Basic
    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @Basic
    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    @OneToMany(mappedBy = "user")
    private Set<RoleEntity> workflows = new HashSet<>();
}
