package springboot_university.security.auth.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot_university.security.user.request.UserRequest;
import springboot_university.university.request.UniversityRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterUniversityRequest {

    @NotNull(message = "Field required")
    private UserRequest userRequest;

    @NotNull(message = "Field required")
    private UniversityRequest universityRequest;
}