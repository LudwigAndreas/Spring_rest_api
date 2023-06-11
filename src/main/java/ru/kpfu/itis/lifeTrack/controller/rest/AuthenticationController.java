package ru.kpfu.itis.lifeTrack.controller.rest;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lifeTrack.dto.security.LoginDto;
import ru.kpfu.itis.lifeTrack.dto.security.SignupDto;
import ru.kpfu.itis.lifeTrack.dto.security.TokenDto;
import ru.kpfu.itis.lifeTrack.mapper.UserMapper;
import ru.kpfu.itis.lifeTrack.model.user.RefreshTokenEntity;
import ru.kpfu.itis.lifeTrack.model.user.UserEntity;
import ru.kpfu.itis.lifeTrack.repository.RefreshTokenRepo;
import ru.kpfu.itis.lifeTrack.repository.UserRepo;
import ru.kpfu.itis.lifeTrack.security.jwt.SecurityUserDetails;
import ru.kpfu.itis.lifeTrack.security.jwt.SecurityUserDetailsService;
import ru.kpfu.itis.lifeTrack.security.jwt.JwtHelper;

import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepo refreshTokenRepo;
    private final UserRepo userRepo;
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserDetailsService userDetailsService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setOwner(userMapper.detailsToEntity(userDetails));
        refreshTokenRepo.save(refreshToken);

        String accessToken = jwtHelper.generateAccessToken(userDetails);
        String refreshTokenString = jwtHelper.generateRefreshToken(userDetails, refreshToken);

        return new ResponseEntity<>(new TokenDto(userDetails.getId(), accessToken, refreshTokenString), HttpStatus.OK);
    }


    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<?> signup(@Valid @RequestBody SignupDto dto) {
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreatedDate(new java.sql.Date(new Date().getTime()));
        user.setLastUpdatedDate(new java.sql.Date(new Date().getTime()));
        user = userRepo.save(user);
        SecurityUserDetails userDetails = userMapper.entityToDetails(user);

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setOwner(user);
        refreshTokenRepo.save(refreshToken);

        String accessToken = jwtHelper.generateAccessToken(userDetails);
        String refreshTokenString = jwtHelper.generateRefreshToken(userDetails, refreshToken);

        return new ResponseEntity<>(new TokenDto(userDetails.getId(), accessToken, refreshTokenString), HttpStatus.OK);
    }
}
