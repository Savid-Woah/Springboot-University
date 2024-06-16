package springboot_university.security.token.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot_university.security.token.enums.TokenType;
import springboot_university.security.user.model.User;

import java.util.UUID;

import static springboot_university.security.token.enums.TokenType.BEARER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Token")
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "token_id", updatable = false, nullable = false)
    private UUID tokenId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", updatable = false, nullable = false)
    private TokenType type;

    @Column(name = "token", updatable = false, nullable = false)
    private String token;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @Column(name = "expired", nullable = false)
    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    public Token(String token, User user) {

        this.type = BEARER ;
        this.token = token;
        this.revoked = false;
        this.expired = false;
        this.user = user;
    }
}