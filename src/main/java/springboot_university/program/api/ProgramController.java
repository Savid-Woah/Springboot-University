package springboot_university.program.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot_university.program.dto.ProgramDTO;
import springboot_university.program.request.ProgramRequest;
import springboot_university.program.service.ProgramService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("university/api/v1/programs")
public class ProgramController {

    private final ProgramService programService;

    @GetMapping(path = "{page-number}/{page-size}")
    public Page<ProgramDTO> getAllPrograms(
            @PathVariable("page-number") Integer pageNumber,
            @PathVariable("page-size") Integer pageSize
    ) {
        return programService.getAllPrograms(pageNumber, pageSize);
    }

    @PostMapping
    public Map<String, Object> addProgram(@Validated @RequestBody ProgramRequest programRequest) {
        return programService.addProgram(programRequest);
    }
}