package springboot_university.program.mapper;

import org.springframework.stereotype.Component;
import springboot_university.program.dto.ProgramDTO;
import springboot_university.program.model.Program;

import java.util.function.Function;

@Component
public class ProgramDTOMapper implements Function<Program, ProgramDTO> {
    @Override
    public ProgramDTO apply(Program program) {
        return new ProgramDTO(
                program.getProgramId(),
                program.getName(),
                program.getDepartment()
        );
    }
}