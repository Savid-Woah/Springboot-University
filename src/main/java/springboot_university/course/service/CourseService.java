package springboot_university.course.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springboot_university.course.dto.CourseDTO;
import springboot_university.course.mapper.CourseDTOMapper;
import springboot_university.course.model.Course;
import springboot_university.course.repository.CourseRepository;
import springboot_university.course.request.CourseRequest;
import springboot_university.exception.BackendException;
import springboot_university.program.model.Program;
import springboot_university.program.repository.ProgramRepository;
import springboot_university.student.model.Student;
import springboot_university.student.service.StudentService;

import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.UUID;

import static springboot_university.exception.MsgCode.NO_STUDENT_ENROLLED_IN_COURSE;
import static springboot_university.exception.MsgCode.OOPS_ERROR;
import static springboot_university.response.handler.ResponseHandler.generateResponse;
import static springboot_university.response.message.ResponseMessage.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final StudentService studentService;
    private final CourseDTOMapper courseDTOMapper;
    private final CourseRepository courseRepository;
    private final ProgramRepository programRepository;


    public Page<CourseDTO> getAllCourses(Integer pageNumber, Integer pageSize) {

        return courseRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(courseDTOMapper);
    }

    public Map<String, Object> addCourse(CourseRequest courseRequest) {

        Course course = buildCourse(courseRequest);
        CourseDTO courseDTO = courseDTOMapper.apply(courseRepository.save(course));
        return generateResponse(courseDTO, HttpStatus.CREATED, COURSE_ADDED);
    }

    public String compareCourseAndUniversityAgeAverage(UUID courseId, UUID universityId) {

        Integer universityStudentsAgeAverage = studentService.getStudentsAgeAverageByUniversity(universityId);
        Integer courseStudentsAgeAverage = getCourseAgeAverage(courseId);

        return resolveResponseMessage(universityStudentsAgeAverage, courseStudentsAgeAverage);
    }

    public Integer getCourseAgeAverage(UUID courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new BackendException(OOPS_ERROR));

        Set<Student> studentsInCourse = course.getStudents();
        if(studentsInCourse.isEmpty()) throw new BackendException(NO_STUDENT_ENROLLED_IN_COURSE);

        for(Student student: studentsInCourse) {
            System.out.println("Edad de " + student.getName() + " : " + student.getAge());
        }

        OptionalDouble studentsInCourseAgeAverage = studentsInCourse
                .stream()
                .mapToInt(Student::getAge)
                .average();

        if(studentsInCourseAgeAverage.isEmpty()) throw new BackendException(OOPS_ERROR);
        return (int) studentsInCourseAgeAverage.getAsDouble();
    }

    private Course buildCourse(CourseRequest courseRequest) {

        Course course = new Course(courseRequest);
        setCourseProgram(courseRequest, course);
        return course;
    }

    private void setCourseProgram(CourseRequest courseRequest, Course course) {

        UUID programId = courseRequest.getProgramId();

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new BackendException(OOPS_ERROR));

        course.setProgram(program);
    }

    private String resolveResponseMessage(Integer universityStudentsAgeAverage, Integer courseStudentsAgeAverage) {

        System.out.println("Promedio de edad de la universidad: " + universityStudentsAgeAverage);
        System.out.println("Promedio de edad en el curso: " + courseStudentsAgeAverage);

        if(universityStudentsAgeAverage > courseStudentsAgeAverage) {
            return UNIVERSITY_AVG_HIGHER_THAN_COURSE_AVG;
        } else if (courseStudentsAgeAverage > universityStudentsAgeAverage){
            return COURSE_AVG_HIGHER_THAN_UNIVERSITY_AVG;
        } else{
            return COURSE_AVG_EQUAL_TO_UNIVERSITY_AVG;
        }
    }
}