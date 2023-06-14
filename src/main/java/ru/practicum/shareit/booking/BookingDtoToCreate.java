package ru.practicum.shareit.booking;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDtoToCreate {
    @NotNull
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull
    @FutureOrPresent

    private LocalDateTime end;

    private long itemId;

    BookingDtoToCreate(@NotNull @FutureOrPresent LocalDateTime start, @NotNull @FutureOrPresent LocalDateTime end, long itemId) {
        this.start = start;
        this.end = end;
        this.itemId = itemId;
    }

    public static BookingDtoToCreateBuilder builder() {
        return new BookingDtoToCreateBuilder();
    }

    public static class BookingDtoToCreateBuilder {
        private @NotNull @FutureOrPresent LocalDateTime start;
        private @NotNull @FutureOrPresent LocalDateTime end;
        private long itemId;

        BookingDtoToCreateBuilder() {
        }

        public BookingDtoToCreateBuilder start(@NotNull @FutureOrPresent LocalDateTime start) {
            this.start = start;
            return this;
        }

        public BookingDtoToCreateBuilder end(@NotNull @FutureOrPresent LocalDateTime end) {
            this.end = end;
            return this;
        }

        public BookingDtoToCreateBuilder itemId(long itemId) {
            this.itemId = itemId;
            return this;
        }

        public BookingDtoToCreate build() {
            return new BookingDtoToCreate(start, end, itemId);
        }

        public String toString() {
            return "BookingDtoToCreate.BookingDtoToCreateBuilder(start=" + this.start + ", end=" + this.end + ", itemId=" + this.itemId + ")";
        }
    }
}
