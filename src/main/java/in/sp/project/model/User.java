package in.sp.project.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name must be required")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must be required")
    private String email;

    @NotBlank(message = "Password must be required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password; // This should store the hashed password

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming user has a default "USER" role for simplicity
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;  // email is used as username for login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Account is never expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Account is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Credentials are never expired
    }

    
}
