package springboot_university.program.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import springboot_university.course.dto.CourseDTO;
import springboot_university.exception.GlobalExceptionHandler;
import springboot_university.exception.MessageTextResolver;
import springboot_university.program.dto.ProgramDTO;
import springboot_university.program.request.ProgramRequest;
import springboot_university.program.service.ProgramService;
import springboot_university.security.config.filter.JwtAuthenticationFilter;
import springboot_university.security.config.service.JwtService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static springboot_university.response.handler.ResponseHandler.generateResponse;
import static springboot_university.response.message.ResponseMessage.PROGRAM_ADDED;

@WebMvcTest(ProgramController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProgramControllerTest {

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
    private ProgramService programService;
    @Autowired
    private ObjectMapper objectMapper;
    private List<ProgramDTO> programList;

    @BeforeEach
    void setUp() {

        programList = Arrays.asList(
                new ProgramDTO(UUID.randomUUID(), "Systems Engineering", "IT"),
                new ProgramDTO(UUID.randomUUID(), "Business", "Financial")
        );
    }
    @Test
    @DisplayName(value = "Should return program dto page")
    void shouldReturnProgramDTOPage() throws Exception {

        // given "page number and page size"
        int pageNumber = 0;
        int pageSize = 2;

        Page<ProgramDTO> programDTOs = new PageImpl<>(
                programList,
                PageRequest.of(pageNumber, pageSize),
                programList.size()
        );

        // when "mocked behaviour"
        when(programService.getAllPrograms(pageNumber, pageSize)).thenReturn(programDTOs);

        // and "endpoint hit" then "expect the following"
        mockMvc.perform(get("/university/api/v1/programs/{page-number}/{page-size}",
                        pageNumber,
                        pageSize
                ))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(programDTOs)));
    }

    @Test
    @DisplayName(value = "Should add program")
    void shouldAddProgram() throws Exception {

        // given "a program request"
        ProgramRequest programRequest = ProgramRequest
                .builder()
                .name("Systems Engineering")
                .department("IT")
                .universityId(UUID.randomUUID())
                .build();

        // and "a mocked programDTO"
        ProgramDTO programDTO = Mockito.mock(ProgramDTO.class);

        Map<String, Object> response = generateResponse(programDTO, CREATED, PROGRAM_ADDED);

        // when "mocked behaviour"
        when(programService.addProgram(programRequest)).thenReturn(response);

        // and "endpoint hit" then "expect the following"
        mockMvc.perform(post("/university/api/v1/programs/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(programRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}