package in.sp.project.repo;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sp.project.model.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	
	Optional<User> findByEmail(String email);
	Boolean existsByEmail(String email);
}
