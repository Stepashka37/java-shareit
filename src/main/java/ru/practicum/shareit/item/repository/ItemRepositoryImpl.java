package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.dto.mapper.ItemMapper.dtoToModel;
import static ru.practicum.shareit.item.dto.mapper.ItemMapper.modelToDto;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private Map<Long, List<Item>> items = new HashMap<>();
    private long itemId = 1;


    @Override
    public ItemDto createItem(long userId, ItemDto itemDto) {
        Item item = dtoToModel(itemDto);
        if (userId < 1) {
            throw new ValidationException("id пользователя не может быть меньше 1");
        }
        item.setOwner(userId);
        item.setItemId(itemId);
        Item itemToReturn;
        if (!items.containsKey(userId)) {
            items.put(userId, new ArrayList<>(List.of(item)));
            itemToReturn = items.get(userId).stream()
                    .filter(x -> x.getItemId() == itemId)
                    .findAny()
                    .get();
        } else {
            items.get(userId).add(item);
            itemToReturn = items.get(userId).stream()
                    .filter(x -> x.getItemId() == itemId)
                    .findAny()
                    .get();
        }
        ++itemId;
        return modelToDto(itemToReturn);
    }

    @Override
    public ItemDto updateItem(long userId, ItemDto itemDto) {
        Item item = dtoToModel(itemDto);

        if (!items.containsKey(userId)) throw new ItemNotFoundException("Предмет с id" + item.getItemId() + " не найден");
        Item itemFromList = items.get(userId).stream()
                .filter(x -> x.getItemId() == item.getItemId())
                .findAny().orElseThrow(() -> new ItemNotFoundException("Предмет с id" + item.getItemId() + " не найден"));
        if (item.getName() != null && !item.getName().isBlank()) {
            itemFromList.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            itemFromList.setDescription(item.getDescription());
        }
        if (item.getIsAvailable() != null) {
            itemFromList.setIsAvailable(item.getIsAvailable());
        }

        items.get(userId).removeIf(x -> x.getItemId() == item.getItemId());
        items.get(userId).add(itemFromList);
        return modelToDto(itemFromList);
    }

    @Override
    public ItemDto getItemById(long userId, long itemIdFromReq) {
        ItemDto itemFromList =  items.values().stream()
                .flatMap(x -> x.stream())
                        .filter(y -> y.getItemId() == itemIdFromReq)
                                .findAny()
                                .map(x -> modelToDto(x))
                                .orElseThrow(() -> new ItemNotFoundException("Предмет с id" + itemIdFromReq + " не найден"));
        return itemFromList;
    }

    @Override
    public List<ItemDto> getOwnerItems(long userId) {
        return items.get(userId).stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchAvailableItems(String text) {
        if (text == null || text.isBlank()) return new ArrayList<>();

        List<ItemDto> itemList = items.values().stream()
                .flatMap(x -> x.stream()).filter(
                        y -> y.getName().toLowerCase().contains(text.toLowerCase())
                        || y.getDescription().toLowerCase().contains(text.toLowerCase()))
                .filter(x -> x.getIsAvailable() == true)
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());;
        return itemList;
    }
}
