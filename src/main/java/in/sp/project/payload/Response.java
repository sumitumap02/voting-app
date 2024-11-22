package in.sp.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    
    private String message;         
    private boolean success;        
    private T data;                  
    private LocalDateTime timestamp; 
    private int statusCode;          

    public Response(String message, boolean success, int statusCode) {
        this.message = message;
        this.success = success;
        this.timestamp = LocalDateTime.now();  
        this.statusCode = statusCode;
    }

    public Response(String message, boolean success, T data, int statusCode) {
        this.message = message;
        this.success = success;
        this.data = data;
        this.timestamp = LocalDateTime.now(); 
        this.statusCode = statusCode;
    }
}
