package springboot_university.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot_university.course.model.Course;
import springboot_university.course.repository.CourseRepository;
import springboot_university.course.request.EnrollRequest;
import springboot_university.exception.BackendException;
import springboot_university.security.user.model.User;
import springboot_university.student.model.Student;
import springboot_university.student.repository.StudentRepository;
import springboot_university.student.request.StudentRequest;
import springboot_university.university.model.University;
import springboot_university.university.repository.UniversityRepository;

import java.util.*;

import static springboot_university.exception.MsgCode.OOPS_ERROR;
import static springboot_university.exception.MsgCode.UNIVERSITY_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final UniversityRepository universityRepository;

    public void createStudent(StudentRequest studentRequest, User user) {

        Student student = buildStudent(studentRequest, user);
        studentRepository.save(student);
    }

    public Integer getStudentsAgeAverageByUniversity(UUID universityId) {

        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new BackendException(UNIVERSITY_NOT_FOUND));

        List<Student> students = studentRepository.findAllByUniversity(university);

        Integer numberOfStudents = students.size();
        Integer studentsAgeSum = students.stream().mapToInt(Student::getAge).sum();

        return studentsAgeSum / numberOfStudents;
    }

    public String enrollInCourse(EnrollRequest enrollRequest) {

        UUID courseId = enrollRequest.getCourseId();
        UUID studentId = enrollRequest.getStudentId();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BackendException(OOPS_ERROR));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new BackendException(OOPS_ERROR));

        Set<Student> courseStudents = course.getStudents();
        if(courseStudents == null) courseStudents = new HashSet<>();
        courseStudents.add(student);
        courseRepository.save(course);

        Set<Course> studentCourses = student.getCourses();
        if(studentCourses == null) studentCourses = new HashSet<>();
        studentCourses.add(course);
        studentRepository.save(student);

        return "Student enrolled in course successfully!";
    }

    private Student buildStudent(StudentRequest studentRequest, User user) {

        Student student = new Student(studentRequest, user);
        setStudentUniversityRelation(studentRequest, student);
        return student;
    }

    private void setStudentUniversityRelation(StudentRequest studentRequest, Student student) {

        UUID universityId = studentRequest.getUniversityId();
        Optional<University> university = universityRepository.findById(universityId);
        if(university.isEmpty()) throw new BackendException(UNIVERSITY_NOT_FOUND);
        student.setUniversity(university.get());
    }
}