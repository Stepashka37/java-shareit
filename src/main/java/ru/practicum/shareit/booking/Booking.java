package ru.practicum.shareit.booking;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "bookings")
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_date")
    private LocalDateTime start;

    @Column(name = "end_date")
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "item_id",
            referencedColumnName = "id")
    private Item item;

    @OneToOne
    @JoinColumn(name = "booker_id",
            referencedColumnName = "id")
    private User booker;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public Booking(long id, LocalDateTime start, LocalDateTime end, Item item, User booker, BookingStatus status) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.item = item;
        this.booker = booker;
        this.status = status;
    }

    public Booking() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id && start.equals(booking.start) && end.equals(booking.end) && item.equals(booking.item) && booker.equals(booking.booker) && status == booking.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, item, booker, status);
    }

    public static BookingBuilder builder() {
        return new BookingBuilder();
    }


    public static class BookingBuilder {
        private long id;
        private LocalDateTime start;
        private LocalDateTime end;
        private Item item;
        private User booker;
        private BookingStatus status;

        BookingBuilder() {
        }

        public BookingBuilder id(long id) {
            this.id = id;
            return this;
        }

        public BookingBuilder start(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public BookingBuilder end(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public BookingBuilder item(Item item) {
            this.item = item;
            return this;
        }

        public BookingBuilder booker(User booker) {
            this.booker = booker;
            return this;
        }

        public BookingBuilder status(BookingStatus status) {
            this.status = status;
            return this;
        }

        public Booking build() {
            return new Booking(id, start, end, item, booker, status);
        }

        public String toString() {
            return "Booking.BookingBuilder(id=" + this.id + ", start=" + this.start + ", end=" + this.end + ", item=" + this.item + ", booker=" + this.booker + ", status=" + this.status + ")";
        }
    }
}

