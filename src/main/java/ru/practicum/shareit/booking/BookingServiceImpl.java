package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.BookingMapper.dtoToCreateToModel;
import static ru.practicum.shareit.booking.BookingMapper.modelToDto;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public BookingDto createBooking(long userId, BookingDtoToCreate bookingDto) {
        if (bookingDto.getStart().isEqual(bookingDto.getEnd()) ||
                bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new ValidationException("Not valid start and end date");
        }
        User userFromDb = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Item itemFromDb = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        if (itemFromDb.getOwner().getId() == userId) throw new UserNotFoundException("Current user is item host");
        if (!itemFromDb.getIsAvailable()) throw new ItemNotAvailableException("Item is not available");
        Booking bookingToCreate = dtoToCreateToModel(bookingDto);
        bookingToCreate.setBooker(userFromDb);
        bookingToCreate.setStatus(BookingStatus.WAITING);
        bookingToCreate.setItem(itemFromDb);
        Booking bookingCreated = bookingRepository.save(bookingToCreate);
        log.info("Создали бронирование с id{}", bookingCreated.getId());
        return modelToDto(bookingCreated);
    }

    @Override
    public BookingDto approveBooking(long userId, long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        if ((booking.getStatus() == BookingStatus.APPROVED && approved == true)
                || (booking.getStatus() == BookingStatus.REJECTED && approved == false)) {
            throw new ValidationException("Item status is already set");
        }
        if (booking.getItem().getOwner().getId() == userId) {
            if (approved) {
                booking.setStatus(BookingStatus.APPROVED);
                booking = bookingRepository.save(booking);
                log.info("Пользователь с id{} одобрил бронирование с id{}", userId, booking.getId());
            } else {
                booking.setStatus(BookingStatus.REJECTED);
                booking = bookingRepository.save(booking);
                log.info("Пользователь с id{} не одобрил бронирование с id{}", userId, booking.getId());
            }
        } else {
            throw new HostNotFoundException("User is not the item host");
        }

        return modelToDto(booking);
    }

    @Override
    public BookingDto getBooking(long userId, long bookingId) {
        User userFromDb = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId) {
            throw new UserNotFoundException("Data is available for booking author or " +
                    "for item owner");
        }
        log.info("Пользователь с id{} получил данные бронирования с id{}", userId, bookingId);
        return modelToDto(booking);
    }

    @Override
    public List<BookingDto> getUserBookings(long userId, String stateAsString) {
        User userFromDb = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        State state;
        try {
            state = State.valueOf(stateAsString);
        } catch (Exception e) {
            throw new StateValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        List<Booking> result = new ArrayList<>();
        switch (state) {
            case ALL:
                result = bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
                break;
            case CURRENT:
                result = bookingRepository.findAllCurrentBookingsByUser(userId, LocalDateTime.now());
                break;
            case PAST:
                result = bookingRepository.findAllPastBookingsByUser(userId, LocalDateTime.now());
                break;
            case REJECTED:
                result = bookingRepository.findAllRejectedBookingsByUser(userId);
                break;
            case FUTURE:
                result = bookingRepository.findAllFutureBookingsByUser(userId, LocalDateTime.now());
                break;
            case WAITING:
                result = bookingRepository.findAllWaitingBookingsByUser(userId);
                break;
            default:
                throw new StateValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        System.out.println(result);
        log.info("Получили все бронирования пользователя с id{}", userId);
        return result.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllUserItemsBookings(long userId, String stateAsString) {
        User userFromDb = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Long> userItems = itemRepository.findAllByOwnerIdOrderByIdAsc(userId)
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        State state;
        try {
             state = State.valueOf(stateAsString);
        } catch (Exception e) {
            throw new StateValidationException("Unknown state: UNSUPPORTED_STATUS");
        }

        List<Booking> result = new ArrayList<>();

        switch (state) {
            case ALL:
                result = bookingRepository.findAllItemsBookings(userItems);
                break;
            case CURRENT:
                result = bookingRepository.findAllItemsCurrentBookings(userItems, LocalDateTime.now());
                break;
            case PAST:
                result = bookingRepository.findAllItemsPastBookings(userItems, LocalDateTime.now());
                break;
            case REJECTED:
                result = bookingRepository.findAllItemsRejectedBookings(userItems);
                break;
            case FUTURE:
                result = bookingRepository.findAllItemsFutureBookings(userItems, LocalDateTime.now());
                break;
            case WAITING:
                result = bookingRepository.findAllItemsWaitingBookings(userItems);
                break;
            default:
                throw new StateValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        log.info("Получили список бронирований всех предметов пользователя с id{}", userId);
        return result.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }
}
