package springboot_university.security.auth.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot_university.security.user.request.UserRequest;
import springboot_university.student.request.StudentRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterStudentRequest {

    @NotNull(message = "Field required")
    private UserRequest userRequest;

    @NotNull(message = "Field required")
    private StudentRequest studentRequest;
}