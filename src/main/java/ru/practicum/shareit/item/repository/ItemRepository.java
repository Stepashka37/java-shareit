package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    Item createItem(long userId, Item item);

    Item updateItem(long userId, Item item);

    Item getItemById(long userId, long itemId);

    List<Item> getOwnerItems(long userId);

    List<Item> searchAvailableItems(String text);
}
