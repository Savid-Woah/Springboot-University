package springboot_university.security.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springboot_university.security.token.model.Token;
import springboot_university.security.user.enums.Role;
import springboot_university.security.user.enums.Source;
import springboot_university.security.user.request.UserRequest;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static springboot_university.security.user.enums.Source.OAUTH2;
import static springboot_university.security.user.enums.Source.SYSTEM;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private Source source;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private List<Token> tokens;

    private User(UserRequest userRequest, Role role, Source source) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.email = userRequest.getEmail();
        this.password =  passwordEncoder.encode(userRequest.getPassword());
        this.role = role;
        this.source = source;
    }

    public User(String email, Role role, Source source) {

        this.email = email;
        this.password = null;
        this.role = role;
        this.source = source;
    }

    public static User buildForSystemSource(UserRequest userRequest, Role role) {
        return new User(userRequest, role, SYSTEM);
    }

    public static User buildForOAuth2Source(String email, Role role) {
        return new User(email, role, OAUTH2);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}