package ma.ensat.backend.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "DTO pour les articles")
public class ItemDto {
    @Schema(description = "ID de l'article", example = "1")
    private Long id;

    @NotBlank(message = "Item name is required")
    @Size(max = 100, message = "Item name must not exceed 100 characters")
    @Schema(description = "Nom de l'article", example = "Laptop Dell XPS 13", required = true)
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Schema(description = "Prix de l'article", example = "999.99", required = true)
    private BigDecimal price;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "Description de l'article", example = "High-performance laptop with Intel i7 processor")
    private String description;

    @Schema(description = "Date de création", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Date de mise à jour", example = "2024-01-16T14:20:00")
    private LocalDateTime updatedAt;

    // Constructeurs
    public ItemDto() {}

    public ItemDto(Long id, String name, BigDecimal price, String description,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}