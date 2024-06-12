package springboot_university.program.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot_university.program.request.ProgramRequest;

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

    public Program(ProgramRequest programRequest) {
        this.name = programRequest.getName();
        this.department = programRequest.getDepartment();
    }
}