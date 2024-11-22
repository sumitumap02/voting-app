package in.sp.project.impl;

import java.util.List;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import in.sp.project.DTO.ElectionDto;
import in.sp.project.DTO.VoteDto;
import in.sp.project.exception.ResourceNotFoundException;
import in.sp.project.model.Candidate;
import in.sp.project.model.Elections;
import in.sp.project.model.User;
import in.sp.project.model.Vote;
import in.sp.project.payload.PageResponse;
import in.sp.project.repo.CandidateRepository;
import in.sp.project.repo.ElectionRepository;
import in.sp.project.repo.UserRepository;
import in.sp.project.repo.VoteRepository;
import in.sp.project.service.VoteService;

@Service
public class VoteServiceImpl implements VoteService{
	
	@Autowired
	private VoteRepository voteRepo;

	@Autowired
	private CandidateRepository candidateRepo;
	
	@Autowired
	private ElectionRepository electionRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public VoteDto createdVotes(VoteDto voteDetails, Long candidateId, Long eleId, Long userId) {
	    // Fetch entities from repositories by ID
	    Candidate candidate = candidateRepo.findById(candidateId)
	            .orElseThrow(() -> new ResourceNotFoundException("Candidate", "id", candidateId));
	    Elections election = electionRepo.findById(eleId)
	            .orElseThrow(() -> new ResourceNotFoundException("Election", "id", eleId));
	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

	    // Create a new Vote object and set its properties
	    Vote vote = new Vote();
	    vote.setCandidate(candidate);
	    vote.setElection(election);
	    vote.setUser(user);
	    vote.setTimestamp(voteDetails.getTimestamp());

	    // Save the vote entity in the database
	    Vote savedVote = voteRepo.save(vote);

	    // Map the saved Vote entity to VoteDto
	    VoteDto savedVoteDto = modelMapper.map(savedVote, VoteDto.class);

	    // Explicitly map the election field to ElectionDto
	    ElectionDto electionDto = modelMapper.map(savedVote.getElection(), ElectionDto.class);
	    savedVoteDto.setElection(electionDto);  // Set the mapped ElectionDto

	    return savedVoteDto;
	}



	@Override
	public PageResponse getAllVotesDetails(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort =null;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			sort =Sort.by(sortBy).ascending();
		}
		else {
			sort =Sort.by(sortBy).descending();
		}
		
		Pageable page =PageRequest.of(pageNumber-1, pageSize,sort);
		
		Page<Vote> allpage =this.voteRepo.findAll(page);
		List<VoteDto> resultDto = allpage.stream().map((res)->this.modelMapper.map(res, VoteDto.class))
				.collect(Collectors.toList());
		
		PageResponse pageResponse =new PageResponse();
		pageResponse.setContent(resultDto);
		pageResponse.setPageNumber(allpage.getNumber()+1);
		pageResponse.setPageSize(allpage.getSize());
		pageResponse.setTotalElement(allpage.getTotalElements());
		pageResponse.setTotalPages(allpage.getTotalPages());
		pageResponse.setLastPage(allpage.isLast());
		
		return pageResponse;
	}

	@Override
	public VoteDto updatedVotes(VoteDto voteDetails, Long votterId) {
		
		Vote result =this.voteRepo.findById(votterId)
		.orElseThrow(()->new ResourceNotFoundException("Vote","voteId",votterId));
		
		result.setTimestamp(voteDetails.getTimestamp());
	
		Vote updateVotter =this.voteRepo.save(result);
		
		return this.modelMapper.map(updateVotter, VoteDto.class);
	}

	@Override
	public void deleteVotesById(Long votterId) {
		
		Vote result =this.voteRepo.findById(votterId)
				.orElseThrow(()->new ResourceNotFoundException("Vote","voteId",votterId));
				
		this.voteRepo.delete(result);
		
	}

	@Override
	public VoteDto getSingleVotterId(Long votterId) {
		
		Vote result =this.voteRepo.findById(votterId)
				.orElseThrow(()->new ResourceNotFoundException("Vote","voteId",votterId));
				
		
		return this.modelMapper.map(result, VoteDto.class);
	}
}

















