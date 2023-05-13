package ru.practicum.shareit.item.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private ItemMapper itemMapper;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto createItem(@RequestHeader("X-Sharer-User-id") long userId, @Validated(ItemDto.New.class) @RequestBody ItemDto itemDto) {
        Item itemFromRequest = itemMapper.dtoToModel(itemDto);
        Item itemCreated = itemService.createItem(userId, itemFromRequest);
        ItemDto itemDtoFromService = itemMapper.modelToDto(itemCreated);
        return itemDtoFromService;
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-id") long userId, @PathVariable long itemId, @Validated(ItemDto.Update.class)  @RequestBody ItemDto itemDto) {
        itemDto.setId(itemId);
        Item itemFromRequest = itemMapper.dtoToModel(itemDto);
        Item itemUpdated = itemService.updateItem(userId, itemFromRequest);
        ItemDto itemDtoFromService = itemMapper.modelToDto(itemUpdated);
        return itemDtoFromService;
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-id") long userId, @PathVariable long itemId) {
        Item itemRequested = itemService.getItemById(userId, itemId);
        ItemDto itemDtoFromService = itemMapper.modelToDto(itemRequested);
        return itemDtoFromService;
    }

    @GetMapping
    public List<ItemDto> getOwnerItems(@RequestHeader("X-Sharer-User-id") long userId) {
        return itemService.getOwnerItems(userId).stream()
                .map(x -> itemMapper.modelToDto(x))
                .collect(Collectors.toList());

    }

    @GetMapping("/search")
    public List<ItemDto> searchAvailableItems(@RequestHeader("X-Sharer-User-id") long userId, @RequestParam String text){
        return itemService.searchAvailableItems(userId, text).stream()
                .map(x -> itemMapper.modelToDto(x))
                .collect(Collectors.toList());
    }

}
