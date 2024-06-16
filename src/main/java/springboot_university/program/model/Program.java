package springboot_university.program.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;
import springboot_university.course.model.Course;
import springboot_university.program.request.ProgramRequest;
import springboot_university.university.model.University;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Program")
@Table(name = "program")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "program_id", updatable = false, nullable = false)
    private UUID programId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "department", nullable = false)
    private String department;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "university_id", updatable = false, nullable = false)
    private University university;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "program")
    @JsonIgnore
    private List<Course> courses;

    public Program(ProgramRequest programRequest) {
        this.name = programRequest.getName();
        this.department = programRequest.getDepartment();
    }

    public void setRelations(University university) {
        this.university = university;
    }
}