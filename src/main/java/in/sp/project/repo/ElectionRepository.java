package in.sp.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sp.project.model.Elections;

@Repository
public interface ElectionRepository extends JpaRepository<Elections,Long>{

}
