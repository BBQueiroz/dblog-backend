package com.dovisen.dblog.domain.authentication;

import com.dovisen.dblog.domain.user.User;
import com.dovisen.dblog.domain.user.UserRepository;
import com.dovisen.dblog.domain.user.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void loadUserByUsername() {
    }

    @Test
    void registerUser() {
        RegisterDTO registerDTO = new RegisterDTO("login_test", "password_test", UserRole.USER);

        when(userRepository.save(any(User.class))).thenReturn(new User("login_test", "password_test", UserRole.USER));

        User savedUser = authenticationService.registerUser(registerDTO);

        assertNotNull(savedUser);
        assertEquals("login_test", savedUser.getLogin());
        assertEquals(UserRole.USER, savedUser.getRole());

        verify(userRepository, times(1)).save(any(User.class));
    }
}