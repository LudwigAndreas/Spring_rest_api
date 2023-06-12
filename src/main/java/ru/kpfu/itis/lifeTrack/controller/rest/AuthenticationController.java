package ru.kpfu.itis.lifeTrack.controller.rest;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lifeTrack.dto.security.LoginDto;
import ru.kpfu.itis.lifeTrack.dto.security.SignupDto;
import ru.kpfu.itis.lifeTrack.dto.security.TokenDto;
import ru.kpfu.itis.lifeTrack.exception.security.TokenException;
import ru.kpfu.itis.lifeTrack.service.AuthenticationService;

import javax.security.auth.login.LoginException;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            TokenDto token = authenticationService.login(loginDto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<?> signup(@Valid @RequestBody SignupDto dto) {
        try {
            TokenDto token = authenticationService.signup(dto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenDto dto) {
        try {
            authenticationService.logout(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TokenException ignored) {}
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout-all")
    @Transactional
    public ResponseEntity<?> logoutAll(@RequestBody TokenDto dto) {
        try {
            authenticationService.logoutAll(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TokenException ignored) {}
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/access-token")
    public ResponseEntity<?> accessToken(@RequestBody TokenDto dto) {
        try {
            TokenDto token = authenticationService.accessToken(dto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (TokenException ignored) {}
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenDto dto) {
        try {
            TokenDto token = authenticationService.refreshToken(dto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (TokenException ignored) {}
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
