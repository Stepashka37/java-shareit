package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item createItem(long userId, Item item);

    Item updateItem(long userId, Item item);

    Item getItemById(long userId, long itemId);

    List<Item> getOwnerItems(long userId);

    List<Item> searchAvailableItems(long userId, String text);

}
