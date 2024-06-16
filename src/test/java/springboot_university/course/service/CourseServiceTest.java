package springboot_university.course.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot_university.course.mapper.CourseDTOMapper;
import springboot_university.course.model.Course;
import springboot_university.course.repository.CourseRepository;
import springboot_university.course.request.CourseRequest;
import springboot_university.program.model.Program;
import springboot_university.program.repository.ProgramRepository;
import springboot_university.student.model.Student;
import springboot_university.student.service.StudentService;
import springboot_university.university.model.University;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static springboot_university.response.message.ResponseMessage.COURSE_AVG_EQUAL_TO_UNIVERSITY_AVG;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private StudentService studentService;
    @Mock
    private CourseDTOMapper courseDTOMapper;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private ProgramRepository programRepository;
    @InjectMocks
    private CourseService courseService;

    @Test
    @DisplayName(value = "Should add course")
    void shouldAddCourse() {

        // given "an existing program"
        UUID programId = UUID.randomUUID();

        Program program = Program
                .builder()
                .programId(programId)
                .name("Systems Engineering")
                .department("IT")
                .university(Mockito.mock(University.class))
                .build();

        // and "a course request"
        CourseRequest courseRequest = CourseRequest
                .builder()
                .name("Software Engineering")
                .credits(4)
                .programId(programId)
                .build();

        // and "a course built from that request"
        Course course = new Course(courseRequest);
        course.setProgram(program);

        // and "a course to be returned by course repository save"
        Course savedCourse = new Course(courseRequest);
        savedCourse.setCourseId(UUID.randomUUID());
        course.setProgram(program);

        // when "mocked behaviours"
        when(programRepository.findById(programId)).thenReturn(Optional.of(program));
        when(courseRepository.save(course)).thenReturn(savedCourse);

        // and "service called"
        var result = courseService.addCourse(courseRequest);

        // then "expect the following"
        assertNotNull(result);

        // verify interactions
        verify(programRepository, times(1)).findById(programId);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName(value = "Should validate averages")
    void shouldCompareCourseAndUniversityAgeAverage() {

        // given "an existing course id and university id"
        UUID courseId = UUID.randomUUID();
        UUID universityId = UUID.randomUUID();

        // and "existing entities for those ids"
        Course softwareEngineering = Course
                .builder()
                .name("Software Engineering")
                .courseId(courseId)
                .build();

        University americana = University
                .builder()
                .name("americana")
                .universityId(universityId)
                .build();

        // and "existing students related to both entities"
        Student samuel = Student
                .builder()
                .name("Samuel")
                .age(20)
                .university(americana)
                .courses(new HashSet<>(Set.of(softwareEngineering)))
                .build();

        // and "student assign to course"
        softwareEngineering.setStudents(new HashSet<>(Set.of(samuel)));

        // when "mocked behaviour"
        when(studentService.getStudentsAgeAverageByUniversity(universityId)).thenReturn(samuel.getAge());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(softwareEngineering));

        // and "service called"
        var result = courseService.compareCourseAndUniversityAgeAverage(courseId, universityId);

        // then "expect the following"
        assertNotNull(result);
        assertEquals(result, COURSE_AVG_EQUAL_TO_UNIVERSITY_AVG);

        // and "verify interactions"
        verify(studentService, times(1)).getStudentsAgeAverageByUniversity(universityId);
        verify(courseRepository, times(1)).findById(courseId);
    }
}