package ru.practicum.shareit.booking;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
public class BookingDto {

    private long id;

    @NotNull
    private LocalDateTime start;
    @NotNull
    @FutureOrPresent
    private LocalDateTime end;

    private Item item;

    private User booker;

    private BookingStatus status;

    BookingDto(long id, @NotNull LocalDateTime start, @NotNull @FutureOrPresent LocalDateTime end, Item item, User booker, BookingStatus status) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.item = item;
        this.booker = booker;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDto that = (BookingDto) o;
        return id == that.id && start.equals(that.start) && end.equals(that.end) && item.equals(that.item) && booker.equals(that.booker) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, item, booker, status);
    }

    public static BookingDtoBuilder builder() {
        return new BookingDtoBuilder();
    }

    public static class BookingDtoBuilder {
        private long id;
        private @NotNull LocalDateTime start;
        private @NotNull @FutureOrPresent LocalDateTime end;
        private Item item;
        private User booker;
        private BookingStatus status;

        BookingDtoBuilder() {
        }

        public BookingDtoBuilder id(long id) {
            this.id = id;
            return this;
        }

        public BookingDtoBuilder start(@NotNull LocalDateTime start) {
            this.start = start;
            return this;
        }

        public BookingDtoBuilder end(@NotNull @FutureOrPresent LocalDateTime end) {
            this.end = end;
            return this;
        }

        public BookingDtoBuilder item(Item item) {
            this.item = item;
            return this;
        }

        public BookingDtoBuilder booker(User booker) {
            this.booker = booker;
            return this;
        }

        public BookingDtoBuilder status(BookingStatus status) {
            this.status = status;
            return this;
        }

        public BookingDto build() {
            return new BookingDto(id, start, end, item, booker, status);
        }

        public String toString() {
            return "BookingDto.BookingDtoBuilder(id=" + this.id + ", start=" + this.start + ", end=" + this.end + ", item=" + this.item + ", booker=" + this.booker + ", status=" + this.status + ")";
        }
    }
}
