package in.sp.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sp.project.model.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Long> {

}
