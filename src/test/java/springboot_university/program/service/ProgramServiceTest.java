package springboot_university.program.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import springboot_university.exception.BackendException;
import springboot_university.exception.MsgCode;
import springboot_university.program.dto.ProgramDTO;
import springboot_university.program.mapper.ProgramDTOMapper;
import springboot_university.program.model.Program;
import springboot_university.program.repository.ProgramRepository;
import springboot_university.program.request.ProgramRequest;
import springboot_university.university.model.University;
import springboot_university.university.repository.UniversityRepository;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceTest {

    @Mock
    private ProgramDTOMapper programDTOMapper;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private ProgramService programService;

    @Test
    @DisplayName(value = "Should add program")
    void shouldAddProgram() {

        // Given

        // "an existing university"
        UUID universityId = UUID.randomUUID();
        University university = new University();
        university.setUniversityId(universityId);

        // and "a program request"
        ProgramRequest programRequest = ProgramRequest
                .builder()
                .universityId(universityId)
                .name("Systems engineering")
                .department("IT")
                .build();

        // and "a program built from the request"
        Program program = new Program(programRequest);
        program.setUniversity(university);

        // and "a saved program to be returned by the repository"
        UUID programId = UUID.randomUUID();

        Program savedProgram = new Program(programRequest);

        savedProgram.setProgramId(programId);
        savedProgram.setUniversity(university);

        ProgramDTO programDTO = new ProgramDTO(programId, savedProgram.getName(), savedProgram.getDepartment());

        // When
        when(universityRepository.findById(universityId)).thenReturn(Optional.of(university));
        when(programRepository.save(any(Program.class))).thenReturn(savedProgram);
        when(programDTOMapper.apply(any(Program.class))).thenReturn(programDTO);

        var result = programService.addProgram(programRequest);

        // Then
        assertNotNull(result);

        // Verify interactions
        verify(universityRepository, times(1)).findById(universityId);
        verify(programRepository, times(1)).save(any(Program.class));
        verify(programDTOMapper, times(1)).apply(savedProgram);
    }

    @Test
    @DisplayName("Should throw exception on university not found")
    void shouldThrowExceptionOnUniversityNotFound() {

        // Given
        UUID universityId = UUID.randomUUID();

        ProgramRequest programRequest = ProgramRequest
                .builder()
                .name("Systems Engineering")
                .department("IT")
                .universityId(universityId)
                .build();

        // Mocked Behaviour
        when(universityRepository.findById(universityId)).thenReturn(Optional.empty());

        // When & Then
        BackendException exception = assertThrows(BackendException.class, () ->
                programService.addProgram(programRequest));

        assertEquals(MsgCode.UNIVERSITY_NOT_FOUND, exception.getMsgCode());

        verify(universityRepository, times(1)).findById(universityId);
        verifyNoMoreInteractions(universityRepository, programRepository);
    }
}
