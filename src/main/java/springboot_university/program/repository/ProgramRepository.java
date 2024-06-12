package springboot_university.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot_university.program.model.Program;

import java.util.Optional;
import java.util.UUID;

public interface ProgramRepository extends JpaRepository<Program, UUID> {
    Optional<Program> findByNameIgnoreCaseAndDepartmentIgnoreCase(String name, String department);
}