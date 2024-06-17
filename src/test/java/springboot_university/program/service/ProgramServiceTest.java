package springboot_university.program.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot_university.course.model.Course;
import springboot_university.exception.BackendException;
import springboot_university.exception.MsgCode;
import springboot_university.program.dto.ProgramDTO;
import springboot_university.program.mapper.ProgramDTOMapper;
import springboot_university.program.model.Program;
import springboot_university.program.repository.ProgramRepository;
import springboot_university.program.request.ProgramRequest;
import springboot_university.student.model.Student;
import springboot_university.student.service.StudentService;
import springboot_university.university.model.University;
import springboot_university.university.repository.UniversityRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static springboot_university.response.message.ResponseMessage.COURSE_AVG_EQUAL_TO_UNIVERSITY_AVG;
import static springboot_university.response.message.ResponseMessage.PROGRAM_AVG_EQUAL_TO_UNIVERSITY_AVG;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceTest {

    @Mock
    private StudentService studentService;

    @Mock
    private ProgramDTOMapper programDTOMapper;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private ProgramService programService;

    @Test
    @DisplayName(value = "Should add program")
    void shouldAddProgram() {

        // Given

        // "an existing university"
        UUID universityId = UUID.randomUUID();
        University university = new University();
        university.setUniversityId(universityId);

        // and "a program request"
        ProgramRequest programRequest = ProgramRequest
                .builder()
                .universityId(universityId)
                .name("Systems engineering")
                .department("IT")
                .build();

        // and "a program built from the request"
        Program program = new Program(programRequest);
        program.setUniversity(university);

        // and "a saved program to be returned by the repository"
        UUID programId = UUID.randomUUID();

        Program savedProgram = new Program(programRequest);

        savedProgram.setProgramId(programId);
        savedProgram.setUniversity(university);

        ProgramDTO programDTO = new ProgramDTO(programId, savedProgram.getName(), savedProgram.getDepartment());

        // When
        when(universityRepository.findById(universityId)).thenReturn(Optional.of(university));
        when(programRepository.save(any(Program.class))).thenReturn(savedProgram);
        when(programDTOMapper.apply(any(Program.class))).thenReturn(programDTO);

        var result = programService.addProgram(programRequest);

        // Then
        assertNotNull(result);

        // Verify interactions
        verify(universityRepository, times(1)).findById(universityId);
        verify(programRepository, times(1)).save(any(Program.class));
        verify(programDTOMapper, times(1)).apply(savedProgram);
    }

    @Test
    @DisplayName("Should throw exception on university not found")
    void shouldThrowExceptionOnUniversityNotFound() {

        // Given
        UUID universityId = UUID.randomUUID();

        ProgramRequest programRequest = ProgramRequest
                .builder()
                .name("Systems Engineering")
                .department("IT")
                .universityId(universityId)
                .build();

        // Mocked Behaviour
        when(universityRepository.findById(universityId)).thenReturn(Optional.empty());

        // When & Then
        BackendException exception = assertThrows(BackendException.class, () ->
                programService.addProgram(programRequest));

        assertEquals(MsgCode.UNIVERSITY_NOT_FOUND, exception.getMsgCode());

        verify(universityRepository, times(1)).findById(universityId);
        verifyNoMoreInteractions(universityRepository, programRepository);
    }

    @Test
    @DisplayName(value = "Should compare program and university age average")
    void shouldCompareProgramAndUniversityAgeAverage() {

        // given "an existing program id, course id and university id"
        UUID courseId = UUID.randomUUID();
        UUID programId = UUID.randomUUID();
        UUID universityId = UUID.randomUUID();

        // and "existing entities for those ids"
        Program systemsEngineering = Program
                .builder()
                .programId(programId)
                .name("Systems Engineering")
                .department("IT")
                .build();

        Course softwareEngineering = Course
                .builder()
                .courseId(courseId)
                .name("Software Engineering")
                .program(systemsEngineering)
                .build();

        University americana = University
                .builder()
                .universityId(universityId)
                .name("americana")
                .build();

        // and "existing students related to both entities"
        Student samuel = Student
                .builder()
                .name("Samuel")
                .age(20)
                .university(americana)
                .courses(new HashSet<>(Set.of(softwareEngineering)))
                .build();

        // and "course assigned to program"
        systemsEngineering.setCourses(new ArrayList<>(List.of(softwareEngineering)));

        // and "student assigned to course"
        softwareEngineering.setStudents(new HashSet<>(Set.of(samuel)));

        // when "mocked behaviour"
        when(studentService.getStudentsAgeAverageByUniversity(universityId)).thenReturn(samuel.getAge());
        when(programRepository.findById(programId)).thenReturn(Optional.of(systemsEngineering));

        // and "service called"
        var result = programService.compareProgramAndUniversityAgeAverage(programId, universityId);

        // then "expect the following"
        assertNotNull(result);
        assertEquals(result, PROGRAM_AVG_EQUAL_TO_UNIVERSITY_AVG);

        // and "verify interactions"
        verify(studentService, times(1)).getStudentsAgeAverageByUniversity(universityId);
        verify(programRepository, times(1)).findById(programId);
    }
}
