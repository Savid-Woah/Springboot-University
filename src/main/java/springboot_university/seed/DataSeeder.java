package springboot_university.seed;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springboot_university.course.model.Course;
import springboot_university.course.repository.CourseRepository;
import springboot_university.program.model.Program;
import springboot_university.program.repository.ProgramRepository;
import springboot_university.security.user.enums.Role;
import springboot_university.security.user.enums.Source;
import springboot_university.security.user.model.User;
import springboot_university.security.user.repository.UserRepository;
import springboot_university.student.enums.Gender;
import springboot_university.student.model.Student;
import springboot_university.student.repository.StudentRepository;
import springboot_university.university.model.University;
import springboot_university.university.repository.UniversityRepository;

import java.time.LocalDate;
import java.util.*;

@Component
@Transactional
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ProgramRepository programRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniversityRepository universityRepository;

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() {

        University university = seedUniversity();
        Program program = seedProgram(university);
        List<Course> courses = seedCourses(program);
        seedStudents(university);

        System.out.println("University ID: " + university.getUniversityId());
        System.out.println("Course ID: " + courses.getFirst().getCourseId());
    }

    private University seedUniversity() {

        User user = User
                .builder()
                .userId(UUID.randomUUID())
                .email("americana@gmail.com")
                .password(passwordEncoder.encode("americana444"))
                .role(Role.UNIVERSITY)
                .source(Source.SYSTEM)
                .build();

        User savedUser = userRepository.save(user);

        University university = University
                .builder()
                .universityId(UUID.randomUUID())
                .name("Coruniamericana")
                .nit("0000000000000000")
                .user(savedUser)
                .build();

        return universityRepository.save(university);
    }

    private Program seedProgram(University university) {

        Program systemsEngineering = Program
                .builder()
                .programId(UUID.randomUUID())
                .name("Systems Engineering")
                .department("IT")
                .university(university)
                .build();

        return programRepository.save(systemsEngineering);
    }

    private List<Course> seedCourses(Program program) {

        List<Course> courses = new ArrayList<>();

        Course softwareEngineering = Course
                .builder()
                .courseId(UUID.randomUUID())
                .name("Software Engineering")
                .credits(3)
                .program(program)
                .build();

        Course savedSoftwareEngineering = courseRepository.save(softwareEngineering);

        System.out.println("Course ID: " + savedSoftwareEngineering.getCourseId());

        Course databaseAdministration = Course
                .builder()
                .courseId(UUID.randomUUID())
                .name("Database Administration")
                .credits(3)
                .program(program)
                .build();

        Course savedDatabaseAdministration = courseRepository.save(databaseAdministration);

        courses.add(savedSoftwareEngineering);
        courses.add(savedDatabaseAdministration);

        return courses;
    }

    private void seedStudents(University university) {

        User samuelUser = User
                .builder()
                .userId(UUID.randomUUID())
                .email("samuel@gmail.com")
                .password(passwordEncoder.encode("samuel444"))
                .role(Role.UNIVERSITY)
                .source(Source.SYSTEM)
                .build();

        User savedSamuelUser = userRepository.save(samuelUser);

        Student samuel = Student
                .builder()
                .studentId(UUID.randomUUID())
                .name("Samuel")
                .age(20)
                .gender(Gender.MALE)
                .birthday(LocalDate.of(2003, 10, 28))
                .code("S-AMERICANA-432")
                .university(university)
                .user(savedSamuelUser)
                .build();

        studentRepository.save(samuel);

        User davidUser = User
                .builder()
                .userId(UUID.randomUUID())
                .email("david@gmail.com")
                .password(passwordEncoder.encode("david444"))
                .role(Role.UNIVERSITY)
                .source(Source.SYSTEM)
                .build();

        User savedDavidUser = userRepository.save(davidUser);

        Student david = Student
                .builder()
                .studentId(UUID.randomUUID())
                .name("David")
                .age(30)
                .birthday(LocalDate.of(1993, 10, 28))
                .gender(Gender.MALE)
                .code("D-AMERICANA-755")
                .university(university)
                .user(savedDavidUser)
                .build();

        studentRepository.save(david);
    }
}