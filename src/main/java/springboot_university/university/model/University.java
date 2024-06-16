package springboot_university.university.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;
import springboot_university.security.user.model.User;
import springboot_university.university.request.UniversityRequest;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Entity")
@Table(name = "entities")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "university_id", updatable = false, nullable = false)
    private UUID universityId;

    @Column(name = "name", updatable = false, nullable = false)
    private String name;

    @Column(name = "nit", updatable = false, nullable = false)
    private String nit;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    public University(UniversityRequest universityRequest, User user) {

        this.name = universityRequest.getName();
        this.nit = universityRequest.getName();
        this.user = user;
    }
}