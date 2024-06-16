package springboot_university.student.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot_university.student.model.Student;
import springboot_university.university.model.University;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findAllByUniversity(University university);
    Page<Student> findAllByUniversity(University university, Pageable pageable);
}