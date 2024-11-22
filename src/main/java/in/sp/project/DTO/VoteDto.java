package in.sp.project.DTO;

import in.sp.project.model.Candidate;
import in.sp.project.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteDto {

	    private Long id;

	    private User user;

	    private Candidate candidate;
	    
	    private ElectionDto election;

	    private String timestamp;
}
