package springboot_university.security.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static springboot_university.security.user.enums.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    UNIVERSITY(
            Set.of(
                    UNIVERSITY_CREATE,
                    UNIVERSITY_READ,
                    UNIVERSITY_UPDATE,
                    UNIVERSITY_DELETE
            )
    ),

    STUDENT(
            Set.of(

            )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}