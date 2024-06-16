package springboot_university.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import springboot_university.student.enums.Gender;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StudentDTO(

        UUID studentId,
        String name,
        Integer age,
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, timezone = "america/bogota")
        LocalDate birthday,
        Gender gender,
        String code

) implements Serializable {
}