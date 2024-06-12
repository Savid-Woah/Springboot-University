package springboot_university.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot_university.student.model.Student;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}