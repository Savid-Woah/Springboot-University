package springboot_university.program.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot_university.program.dto.ProgramDTO;
import springboot_university.program.request.ProgramRequest;
import springboot_university.program.service.ProgramService;
import springboot_university.security.annotation.WithRateLimitProtection;

import java.util.Map;
import java.util.UUID;

@Tag(name = "Program")
@RestController
@RequiredArgsConstructor
@RequestMapping("university/api/v1/programs/")
public class ProgramController {

    private final ProgramService programService;

    @GetMapping(path = "{page-number}/{page-size}")
    @WithRateLimitProtection
    public Page<ProgramDTO> getAllPrograms(
            @PathVariable("page-number") Integer pageNumber,
            @PathVariable("page-size") Integer pageSize
    ) {
        return programService.getAllPrograms(pageNumber, pageSize);
    }

    @PostMapping(path = "compare-age-average/{program-id}/{university-id}")
    @WithRateLimitProtection
    public String compareProgramAndUniversityAgeAverage(
            @PathVariable("program-id") UUID programId,
            @PathVariable("university-id") UUID universityId
    ) {
        return programService.compareProgramAndUniversityAgeAverage(programId, universityId);
    }

    @PostMapping
    @WithRateLimitProtection
    public Map<String, Object> addProgram(@Validated @RequestBody ProgramRequest programRequest) {
        return programService.addProgram(programRequest);
    }
}