package in.sp.project.exception;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	long fieldValue;
	
	
	public  ResourceNotFoundException(String resourceName,String fieldName,long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.fieldName =fieldName;
		this.resourceName =resourceName;
		this.fieldValue =fieldValue;
	}
}
