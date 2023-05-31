package ru.practicum.shareit.booking;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto createBooking(@RequestHeader("X-Sharer-User-id") long userId, @Validated @RequestBody BookingDtoToCreate bookingDto) {
        BookingDto bookingCreated = bookingService.createBooking(userId, bookingDto);
        return bookingCreated;
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approveBooking(@RequestHeader("X-Sharer-User-id") long userId, @PathVariable long bookingId,
                                     @RequestParam boolean approved) {
        BookingDto bookingApproved = bookingService.approveBooking(userId, bookingId, approved);
        return bookingApproved;
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@RequestHeader("X-Sharer-User-id") long userId, @PathVariable long bookingId) {
        BookingDto bookingDto = bookingService.getBooking(userId, bookingId);
        return bookingDto;
    }

    @GetMapping
    public List<BookingDto> getUserBookings(@RequestHeader("X-Sharer-User-id") long userId,
                                            @RequestParam(value = "state", required = false, defaultValue = "ALL") String state) {
        List<BookingDto> result = bookingService.getUserBookings(userId, state);
        return result;
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllUserItemsBookings(@RequestHeader("X-Sharer-User-id") long userId,
                                                    @RequestParam(value = "state", required = false, defaultValue = "ALL") String state) {
        List<BookingDto> result = bookingService.getAllUserItemsBookings(userId, state);
        return result;
    }
}
