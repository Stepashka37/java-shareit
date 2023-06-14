package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingDtoForItemHost;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.BookingMapper.modelToDtoForItem;
import static ru.practicum.shareit.item.CommentMapper.dtoToModel;
import static ru.practicum.shareit.item.CommentMapper.modelToDto;
import static ru.practicum.shareit.item.ItemMapper.dtoToModel;
import static ru.practicum.shareit.item.ItemMapper.modelToDto;
import static ru.practicum.shareit.item.ItemMapper.modelToDtoWithBookings;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    public final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRequestRepository itemRequestRepository;

    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, BookingRepository bookingRepository, CommentRepository commentRepository, ItemRequestRepository itemRequestRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
        this.itemRequestRepository = itemRequestRepository;
    }


    @Override
    public ItemDto createItem(long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        ItemRequest itemRequest = itemRequestRepository.findById(itemDto.getRequestId()).orElse(null);
        Item itemToSave = dtoToModel(itemDto);
        itemToSave.setOwner(user);
        itemToSave.setRequest(itemRequest);
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
    public ItemDtoWithBookingsAndComments getItemById(long userId, long itemId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Item itemFound = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        List<CommentDtoToReturn> itemComments = commentRepository.findAllByItem(itemId)
                .stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
        ItemDtoWithBookingsAndComments dtoToReturn = modelToDtoWithBookings(itemFound);
        dtoToReturn.setComments(itemComments);
        if (itemFound.getOwner().getId() == userId) {
            List<Booking> itemBookings = bookingRepository.findAllBookingsByItemId(itemId);
            if (itemBookings.size() >= 1) {
                List<Booking> allPastBookings = itemBookings.stream().filter(x -> x.getStart().isBefore(LocalDateTime.now())).collect(Collectors.toList());
                BookingDtoForItemHost lastBooking = itemBookings.stream().filter(x -> x.getStart().isBefore(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Booking::getEnd).reversed())
                        .findFirst().map(x -> modelToDtoForItem(x))
                        .orElse(null);

                List<Booking> allFutureBookings = itemBookings.stream().filter(x -> x.getStart().isAfter(LocalDateTime.now())).collect(Collectors.toList());
                BookingDtoForItemHost nextBooking = itemBookings.stream().filter(x -> x.getStart().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Booking::getStart))
                        .findFirst().map(x -> modelToDtoForItem(x))
                        .orElse(null);
                dtoToReturn.setLastBooking(lastBooking);
                dtoToReturn.setNextBooking(nextBooking);
            }
        }
        return dtoToReturn;
    }

    @Override
    public List<ItemDtoWithBookingsAndComments> getOwnerItems(long userId, Integer from, Integer size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Item> userItems = itemRepository.findAllByOwnerIdOrderByIdAsc(userId, pageable);
        List<ItemDtoWithBookingsAndComments> result = new ArrayList<>();
        for (Item userItem : userItems) {
            ItemDtoWithBookingsAndComments dtoToReturn = modelToDtoWithBookings(userItem);
            List<Booking> itemBookings = bookingRepository.findAllBookingsByItemId(userItem.getId());
            List<CommentDtoToReturn> itemComments = commentRepository.findAllByItem(userItem.getId())
                    .stream()
                    .map(x -> modelToDto(x))
                    .collect(Collectors.toList());
            dtoToReturn.setComments(itemComments);
            if (!itemBookings.isEmpty()) {
                List<Booking> allBookings = itemBookings.stream().filter(x -> x.getEnd().isBefore(LocalDateTime.now())).collect(Collectors.toList());
                /*BookingDtoForItemHost lastBooking = itemBookings.stream().filter(x -> x.getEnd().isBefore(LocalDateTime.now()))
                        .findFirst().map(x -> modelToDtoForItem(x)).get();*/
                BookingDtoForItemHost lastBooking = itemBookings.stream().filter(x -> x.getStart().isBefore(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Booking::getEnd).reversed())
                        .findFirst().map(x -> modelToDtoForItem(x))
                        .orElse(null);

               /* BookingDtoForItemHost nextBooking = itemBookings.stream().filter(x -> x.getStart().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Booking::getStart))
                        .findFirst().map(x -> modelToDtoForItem(x)).get();*/
                BookingDtoForItemHost nextBooking = itemBookings.stream().filter(x -> x.getStart().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Booking::getStart))
                        .findFirst().map(x -> modelToDtoForItem(x))
                        .orElse(null);
                dtoToReturn.setLastBooking(lastBooking);
                dtoToReturn.setNextBooking(nextBooking);
            }
            result.add(dtoToReturn);
        }

        log.info("Получили все предметы пользователя с id{}", userId);
        return result;
    }

    @Override
    public List<ItemDto> searchAvailableItems(long userId, String text, Integer from, Integer size) {
        if (text.isBlank()) return new ArrayList<ItemDto>();
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        List<ItemDto> availableItems = itemRepository.findAllByText(text.toLowerCase(), pageable)
                .stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
        log.info("Получили все доступные предметы по фразе ", text);
        return availableItems;
    }

    @Override
    public CommentDtoToReturn addComment(long userId, long itemId, CommentDtoToCreate dtoToCreate) {

        User userFound = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Item itemFound = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));

        List<Booking> booking = bookingRepository.findAllByBookerIdAndItemIdAndEndBefore(itemId, userId, LocalDateTime.now());

        if (booking.size() == 0) {
            throw new ValidationException("User haven't booked this item");
        }
        Comment commentToSave = dtoToModel(dtoToCreate);
        commentToSave.setItem(itemFound);
        commentToSave.setUser(userFound);
        commentToSave.setCreated(LocalDateTime.now());

        Comment comment = commentRepository.save(commentToSave);
        return modelToDto(comment);
    }
}
