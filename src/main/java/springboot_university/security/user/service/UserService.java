package springboot_university.security.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot_university.security.user.enums.Role;
import springboot_university.security.user.model.User;
import springboot_university.security.user.repository.UserRepository;
import springboot_university.security.user.request.UserRequest;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createSytemUser(UserRequest userRequest, Role role) {

        User user = User.buildForSystemSource(userRequest, role);
        return userRepository.save(user);
    }

    public User createOAuth2User(String email, Role role) {

        User user = User.buildForOAuth2Source(email, role);
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}