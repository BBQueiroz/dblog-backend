package com.dovisen.dblog.domain.authentication;

import com.dovisen.dblog.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
}