package springboot_university.security.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import springboot_university.exception.BackendException;
import springboot_university.security.auth.request.LoginRequest;
import springboot_university.security.auth.request.RegisterStudentRequest;
import springboot_university.security.auth.request.RegisterUniversityRequest;
import springboot_university.security.config.service.JwtService;
import springboot_university.security.user.enums.Role;
import springboot_university.security.user.model.User;
import springboot_university.security.user.request.UserRequest;
import springboot_university.security.user.service.UserService;
import springboot_university.student.request.StudentRequest;
import springboot_university.student.service.StudentService;
import springboot_university.university.request.UniversityRequest;
import springboot_university.university.service.UniversityService;

import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static springboot_university.exception.MsgCode.OOPS_ERROR;
import static springboot_university.response.handler.ResponseHandler.generateResponse;
import static springboot_university.response.message.ResponseMessage.STUDENT_REGISTERED;
import static springboot_university.response.message.ResponseMessage.UNIVERSITY_REGISTERED;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final StudentService studentService;
    private final UniversityService universityService;
    private final AuthenticationManager authenticationManager;

    public String login(LoginRequest loginRequest) {

        try {

            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            User user = userService.getUserByEmail(email).orElseThrow(() -> new BackendException(OOPS_ERROR));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(authToken);
            String jwt = jwtService.generateToken(user);
            jwtService.saveUserToken(user, jwt);
            return jwt;

        } catch (AuthenticationException exception) {
            throw new BackendException(OOPS_ERROR);
        }
    }

    public Map<String, Object> registerUniversity(RegisterUniversityRequest registerUniversityRequest) {

        UserRequest userRequest = registerUniversityRequest.getUserRequest();
        UniversityRequest universityRequest = registerUniversityRequest.getUniversityRequest();
        User user = userService.createSytemUser(userRequest, Role.UNIVERSITY);
        universityService.createUniversity(universityRequest, user);

        return generateResponse(CREATED, UNIVERSITY_REGISTERED);
    }

    public Map<String, Object> registerStudent(RegisterStudentRequest registerStudentRequest) {

        UserRequest userRequest = registerStudentRequest.getUserRequest();
        StudentRequest studentRequest = registerStudentRequest.getStudentRequest();
        User user = userService.createSytemUser(userRequest, Role.STUDENT);
        studentService.createStudent(studentRequest, user);

        return generateResponse(CREATED, STUDENT_REGISTERED);
    }
}