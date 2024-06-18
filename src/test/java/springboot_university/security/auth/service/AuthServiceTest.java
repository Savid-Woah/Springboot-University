package springboot_university.security.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import springboot_university.security.auth.request.LoginRequest;
import springboot_university.security.config.service.JwtService;
import springboot_university.security.user.enums.Role;
import springboot_university.security.user.enums.Source;
import springboot_university.security.user.model.User;
import springboot_university.security.user.service.UserService;
import springboot_university.student.service.StudentService;
import springboot_university.university.service.UniversityService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private UserService userService;
    @Mock
    private CookieService cookieService;
    @Mock
    private StudentService studentService;
    @Mock
    private UniversityService universityService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName(value = "Should login user")
    void shouldLoginUser() {

        // given "a login request"

        String email = "user@gmail.com";
        String password = "user444";

        LoginRequest loginRequest = LoginRequest
                .builder()
                .email(email)
                .password(password)
                .build();

        // and "an existing user to be returned by the user service"
        User user = User
                .builder()
                .userId(UUID.randomUUID())
                .email(email)
                .password(password)
                .role(Role.UNIVERSITY)
                .source(Source.SYSTEM)
                .build();

        // and "an authentication token built from credentials"
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

        // and "a mocked authentication result"
        Authentication authentication = mock(Authentication.class);

        // and "a mocked jwt"
        String jwt = Mockito.anyString();

        // when "mocked behaviours"
        when(userService.getUserByEmail(email)).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(authToken)).thenReturn(authentication);
        when(jwtService.generateToken(user)).thenReturn(jwt);

        // and "service called"
        String result = authService.login(loginRequest, any(HttpServletResponse.class));

        // then "expect the following"
        assertEquals(result, jwt);

        // and "verify interactions"
        verify(userService, times(1)).getUserByEmail(email);
        verify(authenticationManager, times(1)).authenticate(authToken);
        verify(jwtService, times(1)).generateToken(user);
    }
}