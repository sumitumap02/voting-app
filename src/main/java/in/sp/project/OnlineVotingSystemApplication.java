package in.sp.project;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OnlineVotingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineVotingSystemApplication.class, args);
    }

    // ModelMapper Bean
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // BCryptPasswordEncoder Bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CommandLineRunner run(BCryptPasswordEncoder encoder) {
        return args -> {
            // Manually encode a password
            String plainTextPassword = "Pass@123";
            String encodedPassword = encoder.encode(plainTextPassword);

            // Print the encoded password to the console (or log it if you prefer)
            System.out.println("Encoded Password: " + encodedPassword);
        };
    }
}
