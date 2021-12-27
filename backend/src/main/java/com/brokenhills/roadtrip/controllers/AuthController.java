package com.brokenhills.roadtrip.controllers;

import com.brokenhills.roadtrip.models.LoggedUserInfo;
import com.brokenhills.roadtrip.models.LoginRequest;
import com.brokenhills.roadtrip.models.LoginResponse;
import com.brokenhills.roadtrip.services.JwtTokenService;
import com.brokenhills.roadtrip.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping(path = "/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManagerBean;

    public AuthController(UserService userService,
                          JwtTokenService jwtTokenService,
                          AuthenticationManager authenticationManagerBean) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManagerBean = authenticationManagerBean;
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<LoginResponse> generateAuthenticationToken(@RequestBody LoginRequest authenticationRequest) {

        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

            final UserDetails userDetails = userService
                    .loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtTokenService.generateToken(userDetails);

            return ResponseEntity.ok(LoginResponse.builder()
                    .token(token)
                    .user(LoggedUserInfo.builder()
                            .username(userDetails.getUsername())
                            .isEnabled(userDetails.isEnabled())
                            .isNonExpired(userDetails.isAccountNonExpired())
                            .isCredentialsNonExpired(userDetails.isCredentialsNonExpired())
                            .isNonLocked(userDetails.isAccountNonLocked())
                            .role(userDetails.getAuthorities()
                                    .stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .findAny()
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User has no role.")))
                            .build())
                    .build());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new RuntimeException(String.format("User: %s is disabled", username), e);
        } catch (BadCredentialsException e) {
            throw new RuntimeException(String.format("Invalid credentials for user: %s", username), e);
        }
    }
}
