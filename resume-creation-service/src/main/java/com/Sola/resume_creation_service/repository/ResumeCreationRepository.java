package com.Sola.resume_creation_service.repository;

import com.Sola.resume_creation_service.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeCreationRepository extends JpaRepository<Resume, Long> {

//    Optional<Resume> findResumeByName (String resumeName);

//    Optional<Resume> findResumeById (Long resumeId);
}
