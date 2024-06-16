package springboot_university.student.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;
import springboot_university.course.model.Course;
import springboot_university.security.user.model.User;
import springboot_university.student.enums.Gender;
import springboot_university.student.request.StudentRequest;
import springboot_university.university.model.University;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Student")
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "student_id", updatable = false, nullable = false)
    private UUID studentId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, timezone = "america/bogota")
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "code", updatable = false, nullable = false)
    private String code;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "university_id", updatable = false, nullable = false)
    private University university;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students")
    @JsonIgnore
    private Set<Course> courses;

    public Student(StudentRequest studentRequest, User user) {

        this.name = studentRequest.getName();
        this.age = studentRequest.getAge();
        this.birthday = studentRequest.getBirthday();
        this.gender = studentRequest.getGender();
        this.code = studentRequest.getCode();
        this.user = user;
    }
}