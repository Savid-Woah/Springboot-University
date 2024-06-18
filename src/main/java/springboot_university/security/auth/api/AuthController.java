package springboot_university.security.auth.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot_university.security.annotation.WithRateLimitProtection;
import springboot_university.security.auth.request.LoginRequest;
import springboot_university.security.auth.request.RegisterStudentRequest;
import springboot_university.security.auth.request.RegisterUniversityRequest;
import springboot_university.security.auth.service.AuthService;

import java.util.Map;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
@RequestMapping("university/api/v1/auth/")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "login")
    @WithRateLimitProtection
    public String login(@Validated @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        return authService.login(loginRequest, httpServletResponse);
    }

    @PostMapping(path = "register-university")
    @WithRateLimitProtection
    public Map<String, Object> registerUniversity(
            @Validated @RequestBody RegisterUniversityRequest registerUniversityRequest
    ) {
        return authService.registerUniversity(registerUniversityRequest);
    }

    @PostMapping(path = "register-student")
    @WithRateLimitProtection
    public Map<String, Object> registerStudent(
            @Validated @RequestBody RegisterStudentRequest registerStudentRequest
    ) {
        return authService.registerStudent(registerStudentRequest);
    }
}
