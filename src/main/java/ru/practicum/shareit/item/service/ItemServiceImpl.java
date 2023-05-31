package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

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
        userRepository.getUserById(userId);
        ItemDto itemCreated = itemRepository.createItem(userId, itemDto);
        log.info("Создали предмет с id{}", itemCreated.getId());
        return itemCreated;
    }

    @Override
    public ItemDto updateItem(long userId, ItemDto itemDto) {
        userRepository.getUserById(userId);
        ItemDto itemUpdated = itemRepository.updateItem(userId, itemDto);
        log.info("Обновили данные предмета с id{}", itemUpdated.getId());
        return itemUpdated;
    }

    @Override
    public ItemDto getItemById(long userId, long itemId) {
        userRepository.getUserById(userId);
        return itemRepository.getItemById(userId, itemId);
    }

    @Override
    public List<ItemDto> getOwnerItems(long userId) {
        userRepository.getUserById(userId);
        List<ItemDto> userItems = itemRepository.getOwnerItems(userId);
        log.info("Получили все предметы пользователя с id{}", userId);
        return userItems;
    }

    @Override
    public List<ItemDto> searchAvailableItems(long userId, String text) {
        userRepository.getUserById(userId);
        List<ItemDto> availableItems = itemRepository.searchAvailableItems(text);
        log.info("Получили все доступные предметы по фразе ", text);
        return availableItems;
    }
}
