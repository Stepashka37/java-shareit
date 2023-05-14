package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemRepository {

    ItemDto createItem(long userId, ItemDto item);

    ItemDto updateItem(long userId, ItemDto item);

    ItemDto getItemById(long userId, long itemId);

    List<ItemDto> getOwnerItems(long userId);

    List<ItemDto> searchAvailableItems(String text);
}
