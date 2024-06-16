package springboot_university.security.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import springboot_university.exception.MessageTextResolver;
import springboot_university.security.auth.request.LoginRequest;
import springboot_university.security.auth.service.AuthService;
import springboot_university.security.config.filter.JwtAuthenticationFilter;
import springboot_university.security.config.service.JwtService;
import springboot_university.security.config.service.OAuth2AuthenticationSuccessHandler;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private MessageTextResolver messageTextResolver;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private OAuth2AuthenticationSuccessHandler auth2AuthenticationSuccessHandler;

    @InjectMocks
    private AuthController authController;

    @Test
    void shouldLoginUser() throws Exception {

        // given "a login request"
        LoginRequest loginRequest = LoginRequest
                .builder()
                .email("test@example.com")
                .password("password")
                .build();

        // and "a mocked token"
        String token = anyString();

        // when "mocked behaviour"
        when(authService.login(loginRequest)).thenReturn(token);

        // and "endpoint hit" then "expect the following"
        mockMvc.perform(post("/university/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }
}