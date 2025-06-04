package ma.ensat.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de réponse d'authentification")
public class AuthResponseDto {
    @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Nom d'utilisateur", example = "admin")
    private String username;

    @Schema(description = "Rôle de l'utilisateur", example = "ADMIN")
    private String role;

    @Schema(description = "Message de réponse", example = "Authentication successful")
    private String message;

    // Constructeurs
    public AuthResponseDto() {}

    public AuthResponseDto(String token, String username, String role, String message) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.message = message;
    }

    // Getters et Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}