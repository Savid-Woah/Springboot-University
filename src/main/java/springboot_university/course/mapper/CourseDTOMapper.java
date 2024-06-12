package springboot_university.course.mapper;

import org.springframework.stereotype.Component;
import springboot_university.course.dto.CourseDTO;
import springboot_university.course.model.Course;

import java.util.function.Function;

@Component
public class CourseDTOMapper implements Function<Course, CourseDTO> {
    @Override
    public CourseDTO apply(Course course) {
        return new CourseDTO(
                course.getCourseId(),
                course.getName(),
                course.getCredits()
        );
    }
}