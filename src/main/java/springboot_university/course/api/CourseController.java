package springboot_university.course.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot_university.course.dto.CourseDTO;
import springboot_university.course.request.CourseRequest;
import springboot_university.course.service.CourseService;

import java.util.Map;
import java.util.UUID;

@Tag(name = "Course")
@RestController
@RequiredArgsConstructor
@RequestMapping("university/api/v1/courses/")
public class CourseController {

    private final CourseService courseService;

    @GetMapping(path = "{page-number}/{page-size}")
    public Page<CourseDTO> getAllCourses(
            @PathVariable("page-number") Integer pageNumber,
            @PathVariable("page-size") Integer pageSize
    ) {
        return courseService.getAllCourses(pageNumber, pageSize);
    }

    @GetMapping(path = "compare-age-average/{course-id}/{university-id}")
    public String CompareCourseAndUniversityAgeAverage(
            @PathVariable("course-id") UUID courseId,
            @PathVariable("university-id") UUID universityId
    ) {
        return courseService.compareCourseAndUniversityAgeAverage(courseId, universityId);
    }

    @PostMapping
    public Map<String, Object> addCourse(@Validated @RequestBody CourseRequest courseRequest) {
        return courseService.addCourse(courseRequest);
    }
}