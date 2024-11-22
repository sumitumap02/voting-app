package in.sp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.sp.project.DTO.CandidateDto;
import in.sp.project.payload.Response;
import in.sp.project.service.CandidatedService;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidatedService candidateService;

    @PostMapping("/add")
    public ResponseEntity<Response<CandidateDto>> createCandidate(@RequestBody CandidateDto candidateDetails) {
        try {
            CandidateDto savedCandidate = candidateService.createCandidate(candidateDetails);
            Response<CandidateDto> response = new Response<>("Candidate Created Successfully", true, savedCandidate, HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle error and return a more informative response
            Response<CandidateDto> response = new Response<>("An error occurred: " + e.getMessage(), false, null, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Update Candidate
    @PutMapping("/updated/{candidateId}")
    public ResponseEntity<Response<CandidateDto>> updateCandidate(@RequestBody CandidateDto candidateDetails,
                                                                   @PathVariable Long candidateId) {
        CandidateDto updatedCandidate = candidateService.updateCandidate(candidateDetails, candidateId);
        Response<CandidateDto> response = new Response<>("Candidate Updated Successfully", true, updatedCandidate, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get All Candidates
    @GetMapping("/all")
    public ResponseEntity<Response<List<CandidateDto>>> getAllCandidates() {
        List<CandidateDto> candidates = candidateService.getAllCandidates();
        Response<List<CandidateDto>> response = new Response<>("List of Candidates", true, candidates, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get Single Candidate by ID
    @GetMapping("/single/{candidateId}")
    public ResponseEntity<Response<CandidateDto>> getSingleCandidateById(@PathVariable Long candidateId) {
        CandidateDto candidate = this.candidateService.getCandidateById(candidateId);
        Response<CandidateDto> response = new Response<>("Candidate Found", true, candidate, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete Candidate by ID
    @DeleteMapping("/delete/{candidateId}")
    public ResponseEntity<Response<Void>> deleteCandidateById(@PathVariable Long candidateId) {
        candidateService.deleteCandidateById(candidateId);
        Response<Void> response = new Response<>("Candidate Deleted Successfully", true, null, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
