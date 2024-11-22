package in.sp.project.service;

import org.springframework.stereotype.Service;
import java.util.*;
import in.sp.project.DTO.UserDto;

@Service
public interface UserService {

	UserDto createdUser(UserDto userDetails);
	
	List<UserDto> getAllUser();
	
	UserDto updatedUser(UserDto userDetails,Long userId);
	
    void  deleteUserID(Long userId);
    
    UserDto getUserId(Long userId);
}






























