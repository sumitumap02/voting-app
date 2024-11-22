package in.sp.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sp.project.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

}
