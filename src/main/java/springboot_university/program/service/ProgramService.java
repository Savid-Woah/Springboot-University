package springboot_university.program.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springboot_university.course.model.Course;
import springboot_university.exception.BackendException;
import springboot_university.program.dto.ProgramDTO;
import springboot_university.program.mapper.ProgramDTOMapper;
import springboot_university.program.model.Program;
import springboot_university.program.repository.ProgramRepository;
import springboot_university.program.request.ProgramRequest;
import springboot_university.student.model.Student;
import springboot_university.student.service.StudentService;
import springboot_university.university.model.University;
import springboot_university.university.repository.UniversityRepository;

import java.util.*;

import static springboot_university.exception.MsgCode.*;
import static springboot_university.response.handler.ResponseHandler.generateResponse;
import static springboot_university.response.message.ResponseMessage.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgramService {

    private final StudentService studentService;
    private final ProgramDTOMapper programDTOMapper;
    private final ProgramRepository programRepository;
    private final UniversityRepository universityRepository;

    public Page<ProgramDTO> getAllPrograms(Integer pageNumber, Integer pageSize) {

        return programRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(programDTOMapper);
    }

    public Map<String, Object> addProgram(ProgramRequest programRequest) {

        Program program = buildProgram(programRequest);
        Program savedProgram = programRepository.save(program);
        ProgramDTO programDTO = programDTOMapper.apply(savedProgram);
        return generateResponse(programDTO, HttpStatus.CREATED, PROGRAM_ADDED);
    }

    private Program buildProgram(ProgramRequest programRequest) {

        Program program = new Program(programRequest);
        setProgramRelations(programRequest, program);
        return program;
    }

    private void setProgramRelations(ProgramRequest programRequest, Program program) {

        UUID universityId = programRequest.getUniversityId();
        Optional<University> university = universityRepository.findById(universityId);
        if(university.isEmpty()) throw new BackendException(UNIVERSITY_NOT_FOUND);
        program.setRelations(university.get());
    }

    public String compareProgramAndUniversityAgeAverage(UUID programId, UUID universityId) {

        Integer universityStudentsAgeAverage = studentService.getStudentsAgeAverageByUniversity(universityId);
        Integer programStudentsAgeAverage = getProgramAgeAverage(programId);

        return resolveResponseMessage(universityStudentsAgeAverage, programStudentsAgeAverage);
    }

    private Integer getProgramAgeAverage(UUID programId) {

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new BackendException(PROGRAM_NOT_FOUND));

        List<Course> programCourses = program.getCourses();

        OptionalDouble studentsInProgramAgeSum = programCourses.stream()
                .flatMap(course -> course.getStudents().stream())
                .mapToInt(Student::getAge)
                .average();

        if(studentsInProgramAgeSum.isPresent()) {
            return (int) studentsInProgramAgeSum.getAsDouble();
        } else{
            throw new BackendException(OOPS_ERROR);
        }
    }

    private String resolveResponseMessage(Integer universityStudentsAgeAverage, Integer programStudentsAgeAverage) {

        System.out.println("Promedio de edad de la universidad: " + universityStudentsAgeAverage);
        System.out.println("Promedio de edad en el programa: " + programStudentsAgeAverage);

        if(universityStudentsAgeAverage > programStudentsAgeAverage) {
            return UNIVERSITY_AVG_HIGHER_THAN_PROGRAM_AVG;
        } else if (programStudentsAgeAverage > universityStudentsAgeAverage){
            return PROGRAM_AVG_HIGHER_THAN_UNIVERSITY_AVG;
        } else{
            return PROGRAM_AVG_EQUAL_TO_UNIVERSITY_AVG;
        }
    }
}