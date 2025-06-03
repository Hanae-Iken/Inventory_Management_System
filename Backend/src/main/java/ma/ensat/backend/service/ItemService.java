package ma.ensat.backend.service;

import ma.ensat.backend.dto.ItemDto;
import ma.ensat.backend.entity.Item;
import ma.ensat.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<ItemDto> getAllItems() {
        return itemRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<ItemDto> getItemById(Long id) {
        return itemRepository.findById(id)
                .map(this::convertToDto);
    }

    public ItemDto createItem(ItemDto itemDto) {
        Item item = convertToEntity(itemDto);
        Item savedItem = itemRepository.save(item);
        return convertToDto(savedItem);
    }

    public Optional<ItemDto> updateItem(Long id, ItemDto itemDto) {
        return itemRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setName(itemDto.getName());
                    existingItem.setPrice(itemDto.getPrice());
                    existingItem.setDescription(itemDto.getDescription());
                    return convertToDto(itemRepository.save(existingItem));
                });
    }

    public boolean deleteItem(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ItemDto convertToDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }

    private Item convertToEntity(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        item.setDescription(itemDto.getDescription());
        return item;
    }
}
