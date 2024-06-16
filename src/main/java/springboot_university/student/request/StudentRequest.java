package springboot_university.student.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot_university.student.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentRequest {

    @NotBlank(message = "Field required")
    private String name;

    @NotNull(message = "Field required")
    private Gender gender;

    @NotNull(message = "Field required")
    private Integer age;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, timezone = "america/bogota")
    @NotNull(message = "Field required")
    private LocalDate birthday;

    @NotBlank(message = "Field required")
    private String code;

    @NotNull(message = "Field required")
    private UUID universityId;
}