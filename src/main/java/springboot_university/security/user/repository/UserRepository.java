package springboot_university.security.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot_university.security.user.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}