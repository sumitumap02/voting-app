package in.sp.project.service;

import org.springframework.stereotype.Service;
import java.util.*;
import in.sp.project.DTO.VoteDto;
import in.sp.project.payload.PageResponse;

@Service
public interface VoteService {

	VoteDto createdVotes(VoteDto voteDetails,Long candidateId,Long eleId,Long userId);
	
	PageResponse getAllVotesDetails(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	VoteDto updatedVotes(VoteDto voteDetails,Long votterId);
	
	public void  deleteVotesById(Long votterId);
 
	VoteDto getSingleVotterId(Long votterId);
	
}
