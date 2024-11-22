package in.sp.project.DTO;

import in.sp.project.model.Elections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateDto {

	private Long id;

	private String name;

	private String party;

	private int voteCount;
	
	
	 private Elections election;
}
