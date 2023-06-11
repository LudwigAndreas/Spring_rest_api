package ru.kpfu.itis.lifeTrack.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lifeTrack.mapper.UserMapper;
import ru.kpfu.itis.lifeTrack.model.user.UserEntity;
import ru.kpfu.itis.lifeTrack.repository.UserRepo;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findUserEntityByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username was not found"));

        SecurityUserDetails userDetails = userMapper.entityToDetails(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return userDetails;
    }

    public SecurityUserDetails findById(String id) {
        return userMapper.entityToDetails(userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user id not found")));
    }
}
