package springboot_university.security.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springboot_university.security.token.model.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    @Query("""
            select t from Token t inner join User u on t.user.userId = u.userId
            where u.userId = :userId and (t.revoked = false and t.expired = false)
            """)
    List<Token> findAllValidTokensByUser(@Param("userId") UUID userId);
    Optional<Token> findByToken(String token);
}