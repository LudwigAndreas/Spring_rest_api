package ru.kpfu.itis.lifeTrack.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Date createdDate;
    private Date lastUpdatedDate;
}
