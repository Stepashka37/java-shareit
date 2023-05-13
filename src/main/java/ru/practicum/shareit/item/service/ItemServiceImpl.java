package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
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
    public Item createItem(long userId, Item item) {
        userRepository.getUserById(userId);
        Item itemCreated = itemRepository.createItem(userId, item);
        log.info("Создали предмет с id{}", itemCreated.getItemId());
        return itemCreated;
    }

    @Override
    public Item updateItem(long userId, Item item) {
        userRepository.getUserById(userId);
        Item itemUpdated = itemRepository.updateItem(userId, item);
        log.info("Обновили данные предмета с id{}", itemUpdated.getItemId());
        return itemUpdated;
    }

    @Override
    public Item getItemById(long userId, long itemId) {
        userRepository.getUserById(userId);
        return itemRepository.getItemById(userId, itemId);
    }

    @Override
    public List<Item> getOwnerItems(long userId) {
        userRepository.getUserById(userId);
        List<Item> userItems = itemRepository.getOwnerItems(userId);
        log.info("Получили все предметы пользователя с id{}", userId);
        return userItems;
    }

    @Override
    public List<Item> searchAvailableItems(long userId, String text) {
        userRepository.getUserById(userId);
        List<Item> availableItems = itemRepository.searchAvailableItems(text);
        log.info("Получили все доступные предметы по фразе ", text);
        return availableItems;
    }
}
