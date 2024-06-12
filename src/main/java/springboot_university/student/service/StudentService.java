package springboot_university.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import springboot_university.student.dto.StudentDTO;
import springboot_university.student.mapper.StudentDTOMapper;
import springboot_university.student.repository.StudentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentDTOMapper studentDTOMapper;
    private final StudentRepository studentRepository;

    public Page<StudentDTO> getAllStudents(Integer pageNumber, Integer pageSize) {

        return studentRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(studentDTOMapper);
    }


}