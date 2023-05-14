package ru.practicum.shareit.item.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;


@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto createItem(@RequestHeader("X-Sharer-User-id") long userId, @Validated(ItemDto.New.class) @RequestBody ItemDto itemDto) {
        ItemDto itemCreated = itemService.createItem(userId, itemDto);
        return itemCreated;
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-id") long userId, @PathVariable long itemId, @Validated(ItemDto.Update.class)  @RequestBody ItemDto itemDto) {
        itemDto.setId(itemId);
        ItemDto itemUpdated = itemService.updateItem(userId, itemDto);
        return itemUpdated;
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-id") long userId, @PathVariable long itemId) {
        ItemDto itemRequested = itemService.getItemById(userId, itemId);
        return itemRequested;
    }

    @GetMapping
    public List<ItemDto> getOwnerItems(@RequestHeader("X-Sharer-User-id") long userId) {
        return itemService.getOwnerItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchAvailableItems(@RequestHeader("X-Sharer-User-id") long userId, @RequestParam String text) {
        return itemService.searchAvailableItems(userId, text);
    }

}
