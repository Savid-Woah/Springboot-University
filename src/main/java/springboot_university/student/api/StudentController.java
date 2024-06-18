package springboot_university.student.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot_university.course.request.EnrollRequest;
import springboot_university.security.annotation.WithRateLimitProtection;
import springboot_university.student.service.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("university/api/v1/students/")
public class StudentController {

    private final StudentService studentService;

    @PostMapping(path = "enroll-in-course")
    @WithRateLimitProtection
    public String enrollInCourse(@Validated @RequestBody EnrollRequest enrollRequest) {
        return studentService.enrollInCourse(enrollRequest);
    }
}