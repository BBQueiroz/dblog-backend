package com.dovisen.dblog.domain.authentication;

import com.dovisen.dblog.domain.user.User;
import com.dovisen.dblog.domain.user.UserRepository;
import com.dovisen.dblog.infra.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;


    public AuthenticationService(UserRepository usersRepository) {
        this.userRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails users = userRepository.findByLogin(username);
        if (users == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return users;
    }


    public User registerUser(RegisterDTO registerDto){
        if (registerDto == null) throw new IllegalArgumentException("Registro inválido: RegisterDTO não pode ser nulo");


        if (userRepository.findByLogin(registerDto.login()) != null) throw new IllegalArgumentException("Usuário já existe");

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        User newUser = new User(registerDto.login(), encryptedPassword, registerDto.role() );

        return userRepository.save(newUser);
    }
}