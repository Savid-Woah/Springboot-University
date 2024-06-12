package springboot_university.student.mapper;

import org.springframework.stereotype.Component;
import springboot_university.student.dto.StudentDTO;
import springboot_university.student.model.Student;

import java.util.function.Function;

@Component
public class StudentDTOMapper implements Function<Student, StudentDTO> {
    @Override
    public StudentDTO apply(Student student) {
        return new StudentDTO(
                student.getStudentId(),
                student.getName(),
                student.getAge(),
                student.getBirthday(),
                student.getGender(),
                student.getCode()
        );
    }
}