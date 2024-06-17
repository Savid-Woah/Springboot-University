package springboot_university.course.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import springboot_university.course.dto.CourseDTO;
import springboot_university.course.request.CourseRequest;
import springboot_university.course.service.CourseService;
import springboot_university.exception.GlobalExceptionHandler;
import springboot_university.exception.MessageTextResolver;
import springboot_university.security.config.filter.JwtAuthenticationFilter;
import springboot_university.security.config.service.JwtService;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static springboot_university.response.handler.ResponseHandler.generateResponse;
import static springboot_university.response.message.ResponseMessage.*;

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

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
    private CourseService courseService;
    @Autowired
    private ObjectMapper objectMapper;
    private List<CourseDTO> courseList;

    @BeforeEach
    void setUp() {

        courseList = Arrays.asList(
                new CourseDTO(UUID.randomUUID(), "Course 1", 20),
                new CourseDTO(UUID.randomUUID(), "Course 2", 25)
        );
    }

    @Test
    @DisplayName(value = "Should get all courses")
    void shouldGetAllCourses() throws Exception {

        Page<CourseDTO> page = new PageImpl<>(courseList);
        when(courseService.getAllCourses(any(Integer.class), any(Integer.class))).thenReturn(page);

        mockMvc.perform(get("/university/api/v1/courses/1/10"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "Should compare course and university age average")
    void shouldCompareCourseAndUniversityAgeAverage() throws Exception {

        // given "the list of valid responses"
        String[] validResponses = {
                UNIVERSITY_AVG_HIGHER_THAN_COURSE_AVG,
                COURSE_AVG_HIGHER_THAN_UNIVERSITY_AVG,
                COURSE_AVG_EQUAL_TO_UNIVERSITY_AVG
        };

        // and "an expected response"
        String response = validResponses[new Random().nextInt(validResponses.length)];

        // and "the path variables"
        UUID courseId = UUID.randomUUID();
        UUID universityId = UUID.randomUUID();

        // when "mocked behaviour"
        when(courseService.compareCourseAndUniversityAgeAverage(courseId, universityId))
                .thenReturn(response);

        // and "endpoint hit" then "expect the following"
        mockMvc.perform(get("/university/api/v1/courses/compare-age-average/{course-id}/{university-id}",
                        courseId,
                        universityId))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName(value = "Should add course")
    void shouldAddCourse() throws Exception {

        // given "a course request"
        CourseRequest courseRequest = CourseRequest
                .builder()
                .name("Systems Engineering")
                .credits(3)
                .programId(UUID.randomUUID())
                .build();

        // and "an expected response"
        Map<String, Object> response = generateResponse(CREATED, COURSE_ADDED);

        // and "mocked behaviour"
        when(courseService.addCourse(courseRequest)).thenReturn(response);

        // and "endpoint hit" then "expect the following"
        mockMvc.perform(post("/university/api/v1/courses/")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}
