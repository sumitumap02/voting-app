package in.sp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sp.project.model.User;
import in.sp.project.payload.JwtAuthRequest;
import in.sp.project.payload.JwtAuthResponse;
import in.sp.project.repo.UserRepository;
import in.sp.project.security.CustomUserDetailService;
import in.sp.project.security.JwtTokenHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth/")
@Tag(name="AuthController", description="APIs for Authentication")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtHelper;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Operation(summary = "Login", description = "Login and generate JWT token")
    @ApiResponse(responseCode = "200", description = "JWT Token generated successfully")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtRequest) {
        this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        UserDetails userDetails = userDetailService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setJwtToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody JwtAuthRequest jwtRequest) {
        if (jwtRequest.getUsername() == null || jwtRequest.getPassword() == null) {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }

        // Check if the email (username) already exists
        if (userRepository.existsByEmail(jwtRequest.getUsername())) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        // Encrypt the password using BCryptPasswordEncoder
        String encryptedPassword = passwordEncoder.encode(jwtRequest.getPassword());

        // Create a new user and set the encrypted password
        User user = new User();
        user.setEmail(jwtRequest.getUsername());  // Store email in 'email' field
        user.setPassword(encryptedPassword);  // Store encrypted password (not plain text)
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }


    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> exceptionHandler(BadCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
