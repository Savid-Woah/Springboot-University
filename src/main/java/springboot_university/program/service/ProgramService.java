package springboot_university.program.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springboot_university.program.dto.ProgramDTO;
import springboot_university.program.mapper.ProgramDTOMapper;
import springboot_university.program.model.Program;
import springboot_university.program.repository.ProgramRepository;
import springboot_university.program.request.ProgramRequest;

import java.util.Map;

import static springboot_university.response.handler.ResponseHandler.generateResponse;
import static springboot_university.response.message.ResponseMessage.PROGRAM_ADDED;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramDTOMapper programDTOMapper;
    private final ProgramRepository programRepository;

    public Page<ProgramDTO> getAllPrograms(Integer pageNumber, Integer pageSize) {

        return programRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(programDTOMapper);
    }

    public Map<String, Object> addProgram(ProgramRequest programRequest) {

        Program program = new Program(programRequest);
        ProgramDTO programDTO = programDTOMapper.apply(programRepository.save(program));
        return generateResponse(programDTO, HttpStatus.CREATED, PROGRAM_ADDED);
    }
}