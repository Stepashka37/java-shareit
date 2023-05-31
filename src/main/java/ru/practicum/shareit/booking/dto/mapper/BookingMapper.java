package ru.practicum.shareit.booking.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingMapper {

    public static BookingDto modelToDto(Booking booking) {
        return BookingDto.builder()
                .start(booking.getStart())
                .finish(booking.getFinish())
                .item(booking.getItem())
                .booker(booking.getBooker())
                .status(booking.getStatus())
                .build();
    }

    public static Booking dtoToModel(BookingDto bookingDto) {
        return Booking.builder()
                .start(bookingDto.getStart())
                .finish(bookingDto.getFinish())
                .item(bookingDto.getItem())
                .booker(bookingDto.getBooker())
                .status(bookingDto.getStatus())
                .build();
    }
}
