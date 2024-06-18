package springboot_university.university.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import springboot_university.security.user.model.User;
import springboot_university.university.dto.UniversityDTO;
import springboot_university.university.mapper.UniversityDTOMapper;
import springboot_university.university.model.University;
import springboot_university.university.repository.UniversityRepository;
import springboot_university.university.request.UniversityRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniversityServiceTest {

    @Mock
    private UniversityDTOMapper universityDTOMapper;

    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private UniversityService universityService;

    @Test
    @DisplayName(value = "Should return a page of university dto")
    void shouldReturnUniversityDTOPage() {

        // given "page number and page size"
        Integer pageNumber = 0;
        Integer pageSize = 1;

        // Create mock User instances
        User mockUser1 = mock(User.class);
        User mockUser2 = mock(User.class);

        // and "some universities"
        University americana = University
                .builder()
                .universityId(UUID.randomUUID())
                .name("Americana")
                .nit("0000000000")
                .user(mockUser1)
                .build();

        University norte = University
                .builder()
                .universityId(UUID.randomUUID())
                .name("Norte")
                .nit("0000000000")
                .user(mockUser2)
                .build();

        // and "those universities put in a list"
        List<University> universities = new ArrayList<>(List.of(americana, norte));

        // and "a page made out of the list with the parameters given"
        Page<University> universitiesPage = new PageImpl<>(
                universities,
                PageRequest.of(pageNumber, pageSize),
                universities.size()
        );

        // and "the page mapped from University to UniversityDTO"
        Page<UniversityDTO> universityDTOPage = universitiesPage
                .map(universityDTOMapper);

        // when "mocked behaviour"
        when(universityRepository.findAll(PageRequest.of(pageNumber, pageSize))).thenReturn(universitiesPage);

        // and "service called"
        var result = universityService.getAllUniversities(pageNumber, pageSize);

        // then
        assertEquals(result, universityDTOPage);
    }

    @Test
    @DisplayName(value = "Should create university")
    void shouldCreateUniversity() {

        // given "a previously created user"
        User user = User.
                builder()
                .userId(UUID.randomUUID())
                .email("university@gmail.com")
                .password("0000")
                .build();

        // and "an university request"
        UniversityRequest universityRequest = UniversityRequest
                .builder()
                .name("Americana")
                .nit("0000000000")
                .build();

        // and: "a university built from the request"
        University university = new University(universityRequest, user);

        // and: "a saved university to be returned by the university repository"
        University savedUniversity = new University(universityRequest, user);
        savedUniversity.setUniversityId(UUID.randomUUID());

        //When

        //Mocked behaviour
        when(universityRepository.save(university)).thenReturn(savedUniversity);

        // Service call
        universityService.createUniversity(universityRequest, user);

        // Then
        verify(universityRepository, times(1)).save(university);
    }
}