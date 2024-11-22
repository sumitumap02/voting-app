package in.sp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import in.sp.project.DTO.ElectionDto;
import in.sp.project.payload.Response;
import in.sp.project.service.CandidatedService;
import in.sp.project.service.ElectionService;

@RestController
@RequestMapping("/api/elections/")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @Autowired
    private CandidatedService candidateService;

    @PostMapping("/add/{candidateId}")
    public ResponseEntity<Response<ElectionDto>> createElection(
            @RequestBody ElectionDto elections, @PathVariable Long candidateId) {

        ElectionDto savedElection = this.electionService.createElection(elections, candidateId);

        // Return the response with a success message
        Response<ElectionDto> response = new Response<>(
                "Election created successfully with ID: " + savedElection.getId(),
                true, savedElection, HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<Response<List<ElectionDto>>> getAllElections() {
        List<ElectionDto> allElections = this.electionService.getAllElections();

        Response<List<ElectionDto>> response = new Response<>(
                "Successfully retrieved all elections.", true, allElections, HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{eleId}")
    public ResponseEntity<Response<String>> deleteElectionById(@PathVariable Long eleId) {

        this.electionService.deletedElections(eleId);

        Response<String> response = new Response<>(
                "Election with ID " + eleId + " deleted successfully.", true, null, HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/single/{eleId}")
    public ResponseEntity<Response<ElectionDto>> getSingleElectionById(@PathVariable Long eleId) {

        ElectionDto result = this.electionService.getSingleElectionById(eleId);

        Response<ElectionDto> response = new Response<>(
                "Successfully retrieved election with ID: " + eleId, true, result, HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
