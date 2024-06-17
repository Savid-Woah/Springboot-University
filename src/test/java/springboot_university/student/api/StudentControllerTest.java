package springboot_university.student.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import springboot_university.course.request.EnrollRequest;
import springboot_university.exception.GlobalExceptionHandler;
import springboot_university.exception.MessageTextResolver;
import springboot_university.security.config.filter.JwtAuthenticationFilter;
import springboot_university.security.config.service.JwtService;
import springboot_university.student.service.StudentService;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static springboot_university.response.message.ResponseMessage.STUDENT_ENROLLED;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private MessageTextResolver messageTextResolver;
    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private StudentService studentService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName(value = "Should enroll user")
    void shouldEnrollUser() throws Exception {

        // given "a valid EnrollRequest object"
        EnrollRequest enrollRequest = EnrollRequest
                .builder()
                .studentId(UUID.randomUUID())
                .courseId(UUID.randomUUID())
                .build();

        // when "mocked behaviour"
        when(studentService.enrollInCourse(enrollRequest)).thenReturn(STUDENT_ENROLLED);

        // and "endpoint hit" then "expect the following"
        mockMvc.perform(post("/university/api/v1/students/enroll-in-course")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(STUDENT_ENROLLED));
    }
}