package in.sp.project.service;

import org.springframework.stereotype.Service;
import java.util.*;
import in.sp.project.DTO.ElectionDto;

@Service
public interface ElectionService {

	ElectionDto createElection(ElectionDto electionDetails,Long candidateId);
	
	public void deletedElections(Long eleId);
	
	List<ElectionDto> getAllElections();
	
	ElectionDto updatedElection(ElectionDto electionDetails,Long eleId);
	
	ElectionDto getSingleElectionById(Long eleId);
	
	
}
