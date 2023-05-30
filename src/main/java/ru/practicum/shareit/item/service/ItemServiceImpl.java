package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.dto.mapper.ItemMapper.dtoToModel;
import static ru.practicum.shareit.item.dto.mapper.ItemMapper.modelToDto;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    public final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ItemDto createItem(long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Item itemToSave = dtoToModel(itemDto);
        itemToSave.setOwner(user);
        Item itemCreated = itemRepository.save(itemToSave);
        log.info("Создали предмет с id{}", itemCreated.getId());
        return modelToDto(itemCreated);
    }

    @Override
    public ItemDto updateItem(long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Item itemFromDb = itemRepository.findById(itemDto.getId())
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        itemFromDb.setOwner(user);
        Item itemToPatch = dtoToModel(itemDto);
        if (itemToPatch.getName() != null && !itemToPatch.getName().isBlank()) {
            itemFromDb.setName(itemToPatch.getName());
        }
        if (itemToPatch.getDescription() != null && !itemToPatch.getDescription().isBlank()) {
            itemFromDb.setDescription(itemToPatch.getDescription());
        }
        if (itemToPatch.getIsAvailable() != null) {
            itemFromDb.setIsAvailable(itemToPatch.getIsAvailable());
        }
        Item itemUpdated = itemRepository.save(itemFromDb);
        log.info("Обновили данные предмета с id{}", itemUpdated.getId());
        return modelToDto(itemUpdated);
    }

    @Override
    public ItemDto getItemById(long userId, long itemId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Item itemFound = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        return modelToDto(itemFound);
    }

    @Override
    public List<ItemDto> getOwnerItems(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Item> userItems = itemRepository.findAllByOwnerId(userId);
        log.info("Получили все предметы пользователя с id{}", userId);
        return userItems.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchAvailableItems(long userId, String text) {
        if (text.isBlank()) return new ArrayList<ItemDto>();
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Item> availableItems = itemRepository.findAllByText(text.toLowerCase());
        log.info("Получили все доступные предметы по фразе ", text);
        return availableItems.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }
}
