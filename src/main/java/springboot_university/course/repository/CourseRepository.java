package springboot_university.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot_university.course.model.Course;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findByNameIgnoreCase(String name);
}