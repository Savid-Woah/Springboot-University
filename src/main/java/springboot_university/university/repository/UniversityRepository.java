package springboot_university.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot_university.university.model.University;

import java.util.UUID;

public interface UniversityRepository extends JpaRepository<University, UUID> {
}