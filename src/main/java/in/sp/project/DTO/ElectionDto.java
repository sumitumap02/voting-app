package in.sp.project.DTO;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import in.sp.project.model.Candidate;
import in.sp.project.model.Vote;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectionDto {

    private Long id;

    private String name;

    private String description;

    private String startDate;

    private String endDate;

    
    private List<Candidate> candidates; 
    @JsonBackReference
    private List<Vote> votes;
   
}
