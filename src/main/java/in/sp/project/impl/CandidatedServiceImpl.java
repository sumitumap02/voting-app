package in.sp.project.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.project.DTO.CandidateDto;
import in.sp.project.exception.ResourceNotFoundException;
import in.sp.project.model.Candidate;
import in.sp.project.model.Elections;
import in.sp.project.repo.CandidateRepository;
import in.sp.project.repo.ElectionRepository;
import in.sp.project.service.CandidatedService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidatedServiceImpl implements CandidatedService {

    @Autowired
    private CandidateRepository candidateRepo;

    @Autowired
    private ElectionRepository electionRepo;

    @Autowired
    private ModelMapper modelMapper;

    // Create a candidate and associate it with an election
 // Create a candidate and associate it with an election
    @Override
    public CandidateDto createCandidate(CandidateDto candidateDetails) {
        if (candidateDetails.getElection() != null && candidateDetails.getElection().getId() != null) {
            // Fetch the election by ID
            Elections election = electionRepo.findById(candidateDetails.getElection().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Election", "id", candidateDetails.getElection().getId()));

            // Create candidate and associate election
            Candidate candidate = this.modelMapper.map(candidateDetails, Candidate.class);
            candidate.setElection(election);

            Candidate savedCandidate = this.candidateRepo.save(candidate);
            return this.modelMapper.map(savedCandidate, CandidateDto.class);
        }

        // If no election is provided, create candidate without election association
        Candidate candidate = this.modelMapper.map(candidateDetails, Candidate.class);
        Candidate savedCandidate = this.candidateRepo.save(candidate);
        return this.modelMapper.map(savedCandidate, CandidateDto.class);
    }


    // Get all candidates and return them as CandidateDto
    @Override
    public List<CandidateDto> getAllCandidates() {
        List<Candidate> candidates = this.candidateRepo.findAll();
        return candidates.stream()
                .map(candidate -> this.modelMapper.map(candidate, CandidateDto.class))
                .collect(Collectors.toList());
    }

    // Update candidate details
    @Override
    public CandidateDto updateCandidate(CandidateDto candidateDetails, Long candidateId) {
        Candidate existingCandidate = this.candidateRepo.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate", "id", candidateId));

        // Update candidate details
        existingCandidate.setName(candidateDetails.getName());
        existingCandidate.setParty(candidateDetails.getParty());
        existingCandidate.setVoteCount(candidateDetails.getVoteCount());

        // If electionId is provided, update the election reference
        if (candidateDetails.getElection() != null) {
            Elections election = electionRepo.findById(candidateDetails.getElection().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Election", "id", candidateDetails.getElection().getId()));
            existingCandidate.setElection(election);
        }

        // Save the updated candidate
        Candidate updatedCandidate = this.candidateRepo.save(existingCandidate);
        return this.modelMapper.map(updatedCandidate, CandidateDto.class);
    }

    // Delete a candidate by ID
    @Override
    public void deleteCandidateById(Long candidateId) {
        Candidate candidate = this.candidateRepo.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate", "id", candidateId));
        this.candidateRepo.delete(candidate);
    }

    // Get a candidate by ID
    @Override
    public CandidateDto getCandidateById(Long candidateId) {
        Candidate candidate = this.candidateRepo.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate", "id", candidateId));
        return this.modelMapper.map(candidate, CandidateDto.class);
    }
}
