package ma.ensat.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO pour l'enregistrement d'un nouvel utilisateur")
public class RegisterDto {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    @Schema(description = "Nom d'utilisateur",
            example = "newuser",
            required = true,
            minLength = 3,
            maxLength = 50)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "Mot de passe",
            example = "securePassword123",
            required = true,
            minLength = 6)
    private String password;

    // Constructeurs
    public RegisterDto() {}

    public RegisterDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}