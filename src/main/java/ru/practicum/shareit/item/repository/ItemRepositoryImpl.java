package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.model.Item;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository{
    private Map<Long, List<Item>> items = new HashMap<>();
    private long itemId = 1;


    @Override
    public Item createItem(long userId, Item item) {
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
            //return itemToReturn; //как то получить предмет из списка
        } else {
            items.get(userId).add(item);
            itemToReturn = items.get(userId).stream()
                    .filter(x -> x.getItemId() == itemId)
                    .findAny()
                    .get();

            //return itemToReturn;
        }
        ++itemId;
        System.out.println("Текущий айди" + itemId);
        return itemToReturn;

    }

    @Override
    public Item updateItem(long userId, Item item) {
        if (!items.containsKey(userId)) throw new ItemNotFoundException("Предмет с id" + item.getItemId() + " не найден");
        Item itemFromList = items.get(userId).stream()
                .filter(x -> x.getItemId() == item.getItemId())
                .findAny().orElseThrow(() -> new ItemNotFoundException("Предмет с id" + item.getItemId() + " не найден"));
        if(item.getName() != null && !item.getName().isBlank()) {
            itemFromList.setName(item.getName());
        }
        if(item.getDescription() != null && !item.getDescription().isBlank()) {
            itemFromList.setDescription(item.getDescription());
        }
        if(item.getIsAvailable() != null) {
            itemFromList.setIsAvailable(item.getIsAvailable());
        }


        items.get(userId).removeIf(x -> x.getItemId() == item.getItemId());
        items.get(userId).add(itemFromList);
        Item itemToReturn = items.get(userId).stream()
                .filter(x -> x.getItemId() == item.getItemId())
                .findAny()
                .get();
        return itemFromList;
    }

    @Override
    public Item getItemById(long userId, long itemIdFromReq) {
        Item itemFromList =  items.values().stream()
                .flatMap(x -> x.stream())
                        .filter(y -> y.getItemId() == itemIdFromReq)
                                .findAny()
                                .orElseThrow(() -> new ItemNotFoundException("Предмет с id" + itemIdFromReq + " не найден"));

        return itemFromList;
    }

    @Override
    public List<Item> getOwnerItems(long userId) {
        List<Item> userItems = items.get(userId);
        return userItems;
    }

    @Override
    public List<Item> searchAvailableItems(String text) {
        if (text.isBlank() || text == null) return new ArrayList<>();

        List<Item> itemList = items.values().stream()
                .flatMap(x -> x.stream()).filter(
                        y -> y.getName().toLowerCase().contains(text.toLowerCase())
                        || y.getDescription().toLowerCase().contains(text.toLowerCase()))
                .filter(x -> x.getIsAvailable() == true)
                .collect(Collectors.toList());;
        return itemList;
    }
}
