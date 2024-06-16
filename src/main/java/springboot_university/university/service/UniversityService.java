package springboot_university.university.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import springboot_university.security.user.model.User;
import springboot_university.university.dto.UniversityDTO;
import springboot_university.university.mapper.UniversityDTOMapper;
import springboot_university.university.model.University;
import springboot_university.university.repository.UniversityRepository;
import springboot_university.university.request.UniversityRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityDTOMapper universityDTOMapper;
    private final UniversityRepository universityRepository;

    public Page<UniversityDTO> getAllUniversities(Integer pageNumber, Integer pageSize) {

        return universityRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(universityDTOMapper);
    }

    public void createUniversity(UniversityRequest universityRequest, User user) {

        University university = new University(universityRequest, user);
        universityRepository.save(university);
    }
}