package springboot_university.university.mapper;

import org.springframework.stereotype.Component;
import springboot_university.university.dto.UniversityDTO;
import springboot_university.university.model.University;

import java.util.function.Function;

@Component
public class UniversityDTOMapper implements Function<University, UniversityDTO> {
    @Override
    public UniversityDTO apply(University university) {
        return new UniversityDTO(
                university.getUniversityId(),
                university.getName(),
                university.getNit()
        );
    }
}