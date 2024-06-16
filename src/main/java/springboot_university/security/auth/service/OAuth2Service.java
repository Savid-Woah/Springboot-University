package springboot_university.security.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import springboot_university.security.config.service.JwtService;
import springboot_university.security.user.model.User;
import springboot_university.security.user.service.UserService;

import java.util.Optional;

import static springboot_university.security.user.enums.Role.STUDENT;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private static final String EMAIL = "email";
    private final UserService userService;
    private final JwtService jwtService;

    public String getJwtFromOAuth2Flow(DefaultOAuth2User oauth2User) {

        String email = (String) oauth2User.getAttributes().get(EMAIL);
        Optional<User> userOptional = userService.getUserByEmail(email);
        User user = userOptional.orElseGet(() -> userService.createOAuth2User(email, STUDENT));
        String jwt = jwtService.generateToken(user);
        jwtService.saveUserToken(user, jwt);
        return jwt;
    }
}
