package springboot_university.security.auth.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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
import springboot_university.security.auth.request.RegisterStudentRequest;
import springboot_university.security.auth.request.RegisterUniversityRequest;
import springboot_university.security.auth.service.AuthService;
import springboot_university.security.config.filter.JwtAuthenticationFilter;
import springboot_university.security.config.service.JwtService;
import springboot_university.security.config.service.OAuth2AuthenticationSuccessHandler;
import springboot_university.security.user.request.UserRequest;
import springboot_university.student.enums.Gender;
import springboot_university.student.request.StudentRequest;
import springboot_university.university.request.UniversityRequest;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static springboot_university.response.handler.ResponseHandler.generateResponse;
import static springboot_university.response.message.ResponseMessage.STUDENT_REGISTERED;
import static springboot_university.response.message.ResponseMessage.UNIVERSITY_REGISTERED;
import static springboot_university.student.enums.Gender.MALE;

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
    @DisplayName(value = "Should login user")
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
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }

    @Test
    @DisplayName(value = "Should register university")
    void shouldRegisterUniversity() throws Exception {


        // given "a user request"
        UserRequest userRequest = UserRequest
                .builder()
                .email("user@email.com")
                .password("user444")
                .build();

        // and "a university request"
        UniversityRequest universityRequest = UniversityRequest
                .builder()
                .name("Americana")
                .nit("0000000000")
                .build();

        // and "a register university request"
        RegisterUniversityRequest registerUniversityRequest = RegisterUniversityRequest
                .builder()
                .userRequest(userRequest)
                .universityRequest(universityRequest)
                .build();

        // and "an expected result"
        Map<String, Object> response = generateResponse(CREATED, UNIVERSITY_REGISTERED);

        // when "mocked behaviour"
        when(authService.registerUniversity(registerUniversityRequest)).thenReturn(response);

        // and "endpoint hit" then "expect the following"
        mockMvc.perform(post("/university/api/v1/auth/register-university")
                        .content(objectMapper.writeValueAsString(registerUniversityRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName(value = "Should register university")
    void shouldRegisterStudent() throws Exception {

        // given "an existing university id"
        UUID universityId = UUID.randomUUID();

        // and "a user request"
        UserRequest userRequest = UserRequest
                .builder()
                .email("user@email.com")
                .password("user444")
                .build();

        // and "a student request"
        StudentRequest studentRequest = StudentRequest
                .builder()
                .name("Samuel")
                .age(20)
                .gender(MALE)
                .birthday(LocalDate.of(2003,10,28))
                .code("S-AMERICANA-444")
                .universityId(universityId)
                .build();

        // and "a register student request"
        RegisterStudentRequest registerStudentRequest = RegisterStudentRequest
                .builder()
                .userRequest(userRequest)
                .studentRequest(studentRequest)
                .build();

        // and "an expected result"
        Map<String, Object> response = generateResponse(CREATED, STUDENT_REGISTERED);

        // when "mocked behaviour"
        when(authService.registerStudent(registerStudentRequest)).thenReturn(response);

        // and "endpoint hit" then "expect the following"
        mockMvc.perform(post("/university/api/v1/auth/register-student")
                .content(objectMapper.writeValueAsString(registerStudentRequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }
}