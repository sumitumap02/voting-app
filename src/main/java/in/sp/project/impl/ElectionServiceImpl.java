package in.sp.project.impl;

import in.sp.project.DTO.CandidateDto;
import in.sp.project.DTO.ElectionDto;
import in.sp.project.exception.ResourceNotFoundException;
import in.sp.project.model.Candidate;
import in.sp.project.model.Elections;
import in.sp.project.model.Vote;
import in.sp.project.repo.CandidateRepository;
import in.sp.project.repo.ElectionRepository;
import in.sp.project.service.ElectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionServiceImpl implements ElectionService {

    @Autowired
    private CandidateRepository candidateRepo;

    @Autowired
    private ElectionRepository electionRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ElectionDto createElection(ElectionDto electionDetails, Long candidateId) {
        // Fetch candidate
        Candidate candidate = candidateRepo.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate", "candidate id", candidateId));

        // Map ElectionDto to Elections entity
        Elections election = modelMapper.map(electionDetails, Elections.class);

        // Add the candidate to the election
        election.setCandidates(List.of(candidate));

        // Save the election entity
        Elections savedElection = electionRepo.save(election);

        // Manually map to DTO for response
        ElectionDto savedElectionDto = modelMapper.map(savedElection, ElectionDto.class);

        savedElectionDto.setCandidates(savedElection.getCandidates());
        savedElectionDto.setVotes(savedElection.getVotes());

        return savedElectionDto;
    }

    @Override
    public void deletedElections(Long eleId) {
        Elections election = electionRepo.findById(eleId)
                .orElseThrow(() -> new ResourceNotFoundException("Election", "election id", eleId));

        electionRepo.delete(election);
    }

    @Override
    public List<ElectionDto> getAllElections() {
        List<Elections> elections = electionRepo.findAll();

        return elections.stream()
                .map(election -> {
                    ElectionDto dto = modelMapper.map(election, ElectionDto.class);

                    // Manually set candidates and votes in the DTO
                    dto.setCandidates(election.getCandidates());
                    dto.setVotes(election.getVotes());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ElectionDto updatedElection(ElectionDto electionDetails, Long eleId) {
        Elections election = electionRepo.findById(eleId)
                .orElseThrow(() -> new ResourceNotFoundException("Election", "election id", eleId));

        election.setName(electionDetails.getName());
        election.setDescription(electionDetails.getDescription());
        election.setStartDate(electionDetails.getStartDate());
        election.setEndDate(electionDetails.getEndDate());

        Elections updatedElection = electionRepo.save(election);

        return modelMapper.map(updatedElection, ElectionDto.class);
    }

    @Override
    public ElectionDto getSingleElectionById(Long eleId) {
        Elections election = electionRepo.findById(eleId)
                .orElseThrow(() -> new ResourceNotFoundException("Election", "election id", eleId));

        ElectionDto electionDto = modelMapper.map(election, ElectionDto.class);

        // Manually set candidates and votes in the DTO
        electionDto.setCandidates(election.getCandidates());
        electionDto.setVotes(election.getVotes());

        return electionDto;
    }
}
