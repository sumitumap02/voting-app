package in.sp.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.sp.project.security.CustomUserDetailService;
import in.sp.project.security.JwtAuthenticationEntryPoint;
import in.sp.project.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String[] PUBLIC_URLS = {
            "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/v3/api-docs/**", // Correct OpenAPI docs path for Springdoc
            "/swagger-ui/**", // Swagger UI resources
            "/swagger-resources/**", // Swagger resources
            "/swagger-ui.html", // Swagger UI entry page
            "/webjars/**" // WebJars resources (used by Swagger UI)

    };

    @Autowired
    private CustomUserDetailService customUserDetails;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll() // Allow Swagger and Auth URLs
                        .requestMatchers(HttpMethod.GET).permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless
                                                                                                              // session
                                                                                                              // management
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // Handle
                                                                                                                 // unauthorized
                                                                                                                 // requests
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
                                                                                                       // before
                                                                                                       // username-password
                                                                                                       // filter

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetails).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
