package in.sp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.sp.project.DTO.VoteDto;
import in.sp.project.payload.PageResponse;
import in.sp.project.payload.Response;
import in.sp.project.service.VoteService;

import java.util.List;

@RestController
@RequestMapping("/api/vote/")
public class VoteController {

    @Autowired
    private VoteService votterService;


    @PostMapping("/user/{userId}/candidate/{candidateId}/election/{electionId}/votes")
    public ResponseEntity<Response<VoteDto>> createVote(@RequestBody VoteDto voteDetails,
                                                         @PathVariable Long candidateId,
                                                         @PathVariable Long electionId,
                                                         @PathVariable Long userId) {
        try {
            VoteDto savedVote = votterService.createdVotes(voteDetails, candidateId, electionId, userId);
            Response<VoteDto> response = new Response<>(
                    "Vote created successfully with ID: " + savedVote.getId(),
                    true, savedVote, HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Response<VoteDto> response = new Response<>(
                    "Error while creating vote", false, null, HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/votes")
    public ResponseEntity<PageResponse> getAllVotes(
    		@RequestParam(value="pageNumber",defaultValue="1",required=false) Integer pageNumber,
    		@RequestParam(value="pageSize",defaultValue="4",required=false)Integer pageSize,
    		@RequestParam(value="sortBy",defaultValue="Id",required=false)String sortBy,
    		@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir) {
       
          PageResponse  pageResponse=votterService.getAllVotesDetails(pageNumber,pageSize,sortBy,sortDir);
          
            return new ResponseEntity<>( pageResponse, HttpStatus.OK);
        
    }

   
    @GetMapping("/votes/{votterId}")
    public ResponseEntity<Response<VoteDto>> getVoteById(@PathVariable Long votterId) {
        try {
            VoteDto vote = votterService.getSingleVotterId(votterId);
            Response<VoteDto> response = new Response<>(
                    "Successfully retrieved vote with ID: " + votterId, true, vote, HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response<VoteDto> response = new Response<>(
                    "Vote with ID " + votterId + " not found.", false, null, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

   
    @PutMapping("/votes/{votterId}")
    public ResponseEntity<Response<VoteDto>> updateVote(@RequestBody VoteDto voteDetails,
                                                         @PathVariable Long votterId) {
        try {
            VoteDto updatedVote = votterService.updatedVotes(voteDetails, votterId);
            Response<VoteDto> response = new Response<>(
                    "Successfully updated vote with ID: " + votterId, true, updatedVote, HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response<VoteDto> response = new Response<>(
                    "Error while updating vote with ID: " + votterId, false, null, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

  
    @DeleteMapping("/votes/{votterId}")
    public ResponseEntity<Response<String>> deleteVote(@PathVariable Long votterId) {
        try {
            votterService.deleteVotesById(votterId);
            Response<String> response = new Response<>(
                    "Vote with ID " + votterId + " deleted successfully.", true, null, HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response<String> response = new Response<>(
                    "Error while deleting vote with ID: " + votterId, false, null, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
