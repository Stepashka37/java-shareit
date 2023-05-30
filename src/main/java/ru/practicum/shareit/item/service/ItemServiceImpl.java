package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDtoForItemHost;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBookings;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.dto.mapper.BookingMapper.modelToDtoForItem;
import static ru.practicum.shareit.item.dto.mapper.ItemMapper.*;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    public final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
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
    public ItemDtoWithBookings getItemById(long userId, long itemId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Item itemFound = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        ItemDtoWithBookings dtoToReturn = modelToDtoWithBookings(itemFound);
        if (itemFound.getOwner().getId() == userId) {
            List<Booking> itemBookings = bookingRepository.findAllBookingsByItemId(itemId, LocalDateTime.now());
            if(!itemBookings.isEmpty()) {
                BookingDtoForItemHost lastBooking = itemBookings.stream().filter(x -> x.getEnd().isBefore(LocalDateTime.now()))
                        .findFirst().map(x -> modelToDtoForItem(x)).get();

                BookingDtoForItemHost nextBooking = itemBookings.stream().filter(x -> x.getStart().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Booking::getStart))
                        .findFirst().map(x -> modelToDtoForItem(x)).get();
                dtoToReturn.setLastBooking(lastBooking);
                dtoToReturn.setNextBooking(nextBooking);
            }
        }
        return dtoToReturn;
    }

    @Override
    public List<ItemDtoWithBookings> getOwnerItems(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Item> userItems = itemRepository.findAllByOwnerIdOrderByIdAsc(userId);
        List<ItemDtoWithBookings> result = new ArrayList<>();
        for (Item userItem : userItems) {
            ItemDtoWithBookings dtoToReturn = modelToDtoWithBookings(userItem);
            List<Booking> itemBookings = bookingRepository.findAllBookingsByItemId(userItem.getId(), LocalDateTime.now());
            if(!itemBookings.isEmpty()) {
                BookingDtoForItemHost lastBooking = itemBookings.stream().filter(x -> x.getEnd().isBefore(LocalDateTime.now()))
                        .findFirst().map(x -> modelToDtoForItem(x)).get();

                BookingDtoForItemHost nextBooking = itemBookings.stream().filter(x -> x.getStart().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Booking::getStart))
                        .findFirst().map(x -> modelToDtoForItem(x)).get();
                dtoToReturn.setLastBooking(lastBooking);
                dtoToReturn.setNextBooking(nextBooking);
        }
            result.add(dtoToReturn);
            }

        log.info("Получили все предметы пользователя с id{}", userId);
        return result;
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
