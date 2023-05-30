package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBookings;

import java.util.List;

public interface ItemService {

    ItemDto createItem(long userId, ItemDto itemDto);

    ItemDto updateItem(long userId, ItemDto itemDto);

    ItemDtoWithBookings getItemById(long userId, long itemId);

    List<ItemDtoWithBookings> getOwnerItems(long userId);

    List<ItemDto> searchAvailableItems(long userId, String text);

}
