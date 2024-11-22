package in.sp.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="elections")
public class Elections{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;  

    @NotNull
    private String description;  

    @JsonManagedReference
    @OneToMany(mappedBy = "election", fetch = FetchType.LAZY)
    private List<Candidate> candidates;

  
    @OneToMany(mappedBy = "election", fetch = FetchType.LAZY)
    private List<Vote> votes; 

    @NotNull
    private String startDate;  
    
    @NotNull
    private String endDate;  
}













