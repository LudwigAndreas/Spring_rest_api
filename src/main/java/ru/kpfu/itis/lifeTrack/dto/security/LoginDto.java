package ru.kpfu.itis.lifeTrack.dto.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
