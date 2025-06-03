package ma.ensat.backend.controller;

import jakarta.validation.Valid;
import ma.ensat.backend.dto.ItemDto;
import ma.ensat.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@Validated
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Endpoints pour les clients (CUSTOMER et ADMIN)
    
    @GetMapping("/customer/all")
    public ResponseEntity<List<ItemDto>> getAllItemsForCustomer() {
        List<ItemDto> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<ItemDto> getItemByIdForCustomer(@PathVariable Long id) {
        Optional<ItemDto> item = itemService.getItemById(id);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoints pour les administrateurs (ADMIN uniquement)

    @GetMapping("/admin/all")
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
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        boolean deleted = itemService.deleteItem(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}