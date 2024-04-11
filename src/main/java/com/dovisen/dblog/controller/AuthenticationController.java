package com.dovisen.dblog.controller;

import com.dovisen.dblog.domain.authentication.AuthenticationDTO;
import com.dovisen.dblog.domain.authentication.AuthenticationService;
import com.dovisen.dblog.domain.authentication.LoginResponseDTO;
import com.dovisen.dblog.domain.authentication.RegisterDTO;
import com.dovisen.dblog.domain.user.*;
import com.dovisen.dblog.infra.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final AuthenticationService authenticationService;
    private final UserRepository usersRepository;

    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationService authenticationService, UserRepository usersRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.usersRepository = usersRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.login(), authenticationDto.password());

        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDto){
        User newUser;
        try {
            newUser = authenticationService.registerUser(registerDto);
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}