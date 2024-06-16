package springboot_university.security.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import springboot_university.security.user.enums.Role;

import java.io.Serializable;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserDTO(

        UUID userId,
        String email,
        Role role

) implements Serializable {
}