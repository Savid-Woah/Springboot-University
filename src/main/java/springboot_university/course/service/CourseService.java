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

import java.util.Map;

import static springboot_university.response.handler.ResponseHandler.generateResponse;
import static springboot_university.response.message.ResponseMessage.COURSE_ADDED;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseDTOMapper courseDTOMapper;
    private final CourseRepository courseRepository;

    public Page<CourseDTO> getAllCourses(Integer pageNumber, Integer pageSize) {
        return courseRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(courseDTOMapper);
    }

    public Map<String, Object> addCourse(CourseRequest courseRequest) {
        Course course = new Course(courseRequest);
        CourseDTO courseDTO = courseDTOMapper.apply(courseRepository.save(course));
        return generateResponse(courseDTO, HttpStatus.CREATED, COURSE_ADDED);
    }
}