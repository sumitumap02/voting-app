package in.sp.project.service;

import in.sp.project.DTO.CandidateDto;
import java.util.List;

public interface CandidatedService {

    CandidateDto createCandidate(CandidateDto candidateDetails);

    List<CandidateDto> getAllCandidates();

    CandidateDto updateCandidate(CandidateDto candidateDetails, Long candidateId);

    void deleteCandidateById(Long candidateId);

    CandidateDto getCandidateById(Long candidateId);
}
