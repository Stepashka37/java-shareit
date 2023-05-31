package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
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
            } else {
                booking.setStatus(BookingStatus.REJECTED);
                booking = bookingRepository.save(booking);
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
            throw new UserNotFoundException("Получить данные о конкретном бронировании может либо автор бронирования, " +
                    "либо владелец бронируемой вещи");
        }
        return modelToDto(booking);
    }

    @Override
    public List<BookingDto> getUserBookings(long userId, String state) {
        User userFromDb = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Booking> result = new ArrayList<>();
        switch (state) {
            case "ALL":
                result = bookingRepository.findAllByBookerId(userId, Sort.by(Sort.Direction.DESC, "start"));
                break;
            case "CURRENT":
                result = bookingRepository.findAllCurrentBookingsByUser(userId, LocalDateTime.now(), Sort.by(Sort.Direction.DESC, "start"));
                break;
            case "PAST":
                result = bookingRepository.findAllPastBookingsByUser(userId, LocalDateTime.now(), Sort.by(Sort.Direction.DESC, "start"));
                break;
            case "REJECTED":
                result = bookingRepository.findAllRejectedBookingsByUser(userId, Sort.by(Sort.Direction.DESC, "start"));
                break;
            case "FUTURE":
                result = bookingRepository.findAllFutureBookingsByUser(userId, LocalDateTime.now(), Sort.by(Sort.Direction.DESC, "start"));
                break;
            case "WAITING":
                result = bookingRepository.findAllWaitingBookingsByUser(userId, Sort.by(Sort.Direction.DESC, "start"));
                break;
            default:
                throw new StateValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        System.out.println(result);
        return result.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllUserItemsBookings(long userId, String state) {
        User userFromDb = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Long> userItems = itemRepository.findAllByOwnerIdOrderByIdAsc(userId)
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        List<Booking> result = new ArrayList<>();

        switch (state) {
            case "ALL":
                result = bookingRepository.findAllItemsBookings(userItems, Sort.by(Sort.Direction.DESC, "start"));
                break;
            case "CURRENT":
                result = bookingRepository.findAllItemsCurrentBookings(userItems, LocalDateTime.now(), Sort.by(Sort.Direction.DESC, "start"));
                break;
            case "PAST":
                result = bookingRepository.findAllItemsPastBookings(userItems, LocalDateTime.now(), Sort.by(Sort.Direction.DESC, "start"));
                break;
            case "REJECTED":
                result = bookingRepository.findAllItemsRejectedBookings(userItems, Sort.by(Sort.Direction.DESC, "start"));
                break;
            case "FUTURE":
                result = bookingRepository.findAllItemsFutureBookings(userItems, LocalDateTime.now(), Sort.by(Sort.Direction.DESC, "start"));
                break;
            case "WAITING":
                result = bookingRepository.findAllItemsWaitingBookings(userItems, Sort.by(Sort.Direction.DESC, "start"));
                break;
            default:
                throw new StateValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        return result.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }
}
