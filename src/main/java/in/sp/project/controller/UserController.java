package in.sp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sp.project.DTO.UserDto;
import in.sp.project.payload.Response;
import in.sp.project.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping("/add")
    public ResponseEntity<Response<UserDto>> createdUser(@RequestBody UserDto userDetails) {
        UserDto createdUser = this.userService.createdUser(userDetails);

        Response<UserDto> response = new Response<>(
            "User created successfully!",
            true,
            createdUser,
            HttpStatus.CREATED.value()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all users
    @GetMapping("/list")
    public ResponseEntity<Response<List<UserDto>>> getAllUser() {
        List<UserDto> allUsers = this.userService.getAllUser();

        Response<List<UserDto>> response = new Response<>(
            "All users fetched successfully.", 
            true,
            allUsers,
            HttpStatus.OK.value() 
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update user by ID
    @PutMapping("/updated/{userId}")
    public ResponseEntity<Response<UserDto>> updatedUserById(@RequestBody UserDto userDetails,
                                                              @PathVariable Long userId) {
        UserDto updatedUser = this.userService.updatedUser(userDetails, userId);

        Response<UserDto> response = new Response<>(
            "User updated successfully.",
            true, 
            updatedUser,
            HttpStatus.OK.value() 
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete user by ID
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Response<Object>> deleteUserById(@PathVariable Long userId) {
        this.userService.deleteUserID(userId);

        Response<Object> response = new Response<>(
            "User deleted successfully!", 
            true,
            null,
            HttpStatus.OK.value() 
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    public ResponseEntity<Response<UserDto>> getSingleUserById(@PathVariable Long userId){
    	
    	UserDto singleUser =this.userService.getUserId(userId);
    	 Response<UserDto> response = new Response<>(
    	            "User retrive successfully!", true, singleUser,
    	            HttpStatus.OK.value() 
    	        );

    	        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}













