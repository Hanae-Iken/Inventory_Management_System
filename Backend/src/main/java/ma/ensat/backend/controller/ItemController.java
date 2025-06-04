package ma.ensat.backend.controller;

import ma.ensat.backend.dto.ItemDto;
import ma.ensat.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@Validated
@Tag(name = "Items", description = "API de gestion des articles")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Endpoints pour les clients (CUSTOMER et ADMIN)
    @GetMapping("/customer/all")
    @Operation(summary = "Récupérer tous les articles", description = "Accessible aux clients et administrateurs")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des articles récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non autorisé")
    })
    public ResponseEntity<List<ItemDto>> getAllItemsForCustomer() {
        List<ItemDto> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "Récupérer un article par ID", description = "Accessible aux clients et administrateurs")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article trouvé"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé"),
            @ApiResponse(responseCode = "401", description = "Non autorisé")
    })
    public ResponseEntity<ItemDto> getItemByIdForCustomer(
            @Parameter(description = "ID de l'article", required = true) @PathVariable Long id) {
        Optional<ItemDto> item = itemService.getItemById(id);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoints pour les administrateurs (ADMIN uniquement)
    @GetMapping("/admin/all")
    @Operation(summary = "Récupérer tous les articles (Admin)", description = "Accessible aux administrateurs uniquement")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des articles récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non autorisé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    public ResponseEntity<List<ItemDto>> getAllItemsForAdmin() {
        List<ItemDto> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<ItemDto> getItemByIdForAdmin(@PathVariable Long id) {
        Optional<ItemDto> item = itemService.getItemById(id);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/create")
    @Operation(summary = "Créer un nouvel article", description = "Accessible aux administrateurs uniquement")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "401", description = "Non autorisé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    public ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDto itemDto) {
        try {
            ItemDto createdItem = itemService.createItem(itemDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long id, @Valid @RequestBody ItemDto itemDto) {
        Optional<ItemDto> updatedItem = itemService.updateItem(id, itemDto);
        return updatedItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/admin/delete/{id}")
    @Operation(summary = "Supprimer un article", description = "Accessible aux administrateurs uniquement")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Article supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé"),
            @ApiResponse(responseCode = "401", description = "Non autorisé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    public ResponseEntity<Void> deleteItem(
            @Parameter(description = "ID de l'article à supprimer", required = true) @PathVariable Long id) {
        boolean deleted = itemService.deleteItem(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}