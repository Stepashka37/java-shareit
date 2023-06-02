package ru.practicum.shareit.booking;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDtoForItemHost {

    private long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private long bookerId;

    BookingDtoForItemHost(long id, LocalDateTime start, LocalDateTime end, long bookerId) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.bookerId = bookerId;
    }

    public static BookingDtoForItemHostBuilder builder() {
        return new BookingDtoForItemHostBuilder();
    }

    public static class BookingDtoForItemHostBuilder {
        private long id;
        private LocalDateTime start;
        private LocalDateTime end;
        private long bookerId;

        BookingDtoForItemHostBuilder() {
        }

        public BookingDtoForItemHostBuilder id(long id) {
            this.id = id;
            return this;
        }

        public BookingDtoForItemHostBuilder start(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public BookingDtoForItemHostBuilder end(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public BookingDtoForItemHostBuilder bookerId(long bookerId) {
            this.bookerId = bookerId;
            return this;
        }

        public BookingDtoForItemHost build() {
            return new BookingDtoForItemHost(id, start, end, bookerId);
        }

        public String toString() {
            return "BookingDtoForItemHost.BookingDtoForItemHostBuilder(id=" + this.id + ", start=" + this.start + ", end=" + this.end + ", bookerId=" + this.bookerId + ")";
        }
    }
}
