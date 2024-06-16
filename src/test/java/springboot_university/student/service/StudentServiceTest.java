package springboot_university.student.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot_university.exception.BackendException;
import springboot_university.exception.MsgCode;
import springboot_university.security.user.model.User;
import springboot_university.student.mapper.StudentDTOMapper;
import springboot_university.student.model.Student;
import springboot_university.student.repository.StudentRepository;
import springboot_university.student.request.StudentRequest;
import springboot_university.university.model.University;
import springboot_university.university.repository.UniversityRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static springboot_university.security.user.enums.Role.STUDENT;
import static springboot_university.security.user.enums.Source.SYSTEM;
import static springboot_university.student.enums.Gender.MALE;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentDTOMapper studentDTOMapper;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName(value = "Should create student")
    void shouldCreateStudent() {

        // Given "an existing university"
        UUID universityId = UUID.randomUUID();
        University university = new University();
        university.setUniversityId(universityId);

        // and "an existing user"
        User user = new User();
        user.setEmail("student@gmail.com");
        user.setPassword("0000");
        user.setSource(SYSTEM);
        user.setRole(STUDENT);

        // and "a student request"
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setName("Samuel Ballesteros");
        studentRequest.setAge(20);
        studentRequest.setGender(MALE);
        studentRequest.setCode("IUBDA");
        studentRequest.setBirthday(LocalDate.of(2003, 10, 28));
        studentRequest.setUniversityId(universityId);

        // and "a student built from the request"
        Student student = new Student(studentRequest, user);
        student.setUniversity(university);

        // and "a saved student to be returned by the repository"
        Student savedStudent = new Student(studentRequest, user);
        savedStudent.setStudentId(UUID.randomUUID());
        savedStudent.setUniversity(university);

        // When

        // Mocked Behaviour
        when(universityRepository.findById(universityId)).thenReturn(Optional.of(university));
        when(studentRepository.save(student)).thenReturn(savedStudent);

        // Calling service
        studentService.createStudent(studentRequest, user);

        // Then
        verify(universityRepository, times(1)).findById(universityId);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    @DisplayName("Should throw exception on university not found")
    void shouldThrowExceptionOnUniversityNotFound() {

        // Given "an existing university id"
        UUID universityId = UUID.randomUUID();

        // and "an existing user"
        User user = new User();
        user.setEmail("student@gmail.com");
        user.setPassword("0000");
        user.setSource(SYSTEM);
        user.setRole(STUDENT);

        // and "a student request"
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setName("Samuel Ballesteros");
        studentRequest.setAge(20);
        studentRequest.setGender(MALE);
        studentRequest.setCode("IUBDA");
        studentRequest.setBirthday(LocalDate.of(2003, 10, 28));
        studentRequest.setUniversityId(universityId);

        // Mocked Behaviour
        when(universityRepository.findById(universityId)).thenReturn(Optional.empty());

        // When & Then
        BackendException exception = assertThrows(BackendException.class, () ->
                studentService.createStudent(studentRequest, user));

        assertEquals(MsgCode.UNIVERSITY_NOT_FOUND, exception.getMsgCode());

        verify(universityRepository, times(1)).findById(universityId);
        verifyNoMoreInteractions(universityRepository, studentRepository);
    }

    @Test
    @DisplayName(value = "Should return students age average by university")
    void shouldReturnStudentsAgeAverageByUniversity() {

        // given "an existing university id"
        UUID universityId = UUID.randomUUID();

        // and "a university found for that id"
        University university = University
                .builder()
                .name("Americana")
                .nit("0000000000")
                .user(Mockito.mock(User.class))
                .build();

        // and "a list of students to be returned for that university"
        Student samuel = Student
                .builder()
                .studentId(UUID.randomUUID())
                .name("Samuel")
                .age(20)
                .university(university)
                .build();

        Student david = Student
                .builder()
                .studentId(UUID.randomUUID())
                .name("David")
                .age(30)
                .university(university)
                .build();

        List<Student> universityStudents = new ArrayList<>(List.of(samuel, david));

        // and "the expected result, average of age for the university students"
        Integer average = 25;

        // when "mocked behaviours"
        when(universityRepository.findById(universityId)).thenReturn(Optional.of(university));
        when(studentRepository.findAllByUniversity(university)).thenReturn(universityStudents);

        // and "service called"
        Integer result = studentService.getStudentsAgeAverageByUniversity(universityId);

        // then "expect the following"
        assertNotNull(result);
        assertEquals(result, average);
    }
}