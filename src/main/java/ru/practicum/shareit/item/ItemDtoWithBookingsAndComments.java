package ru.practicum.shareit.item;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.BookingDtoForItemHost;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ItemDtoWithBookingsAndComments {

    ItemDtoWithBookingsAndComments(long id, @NotBlank(groups = {ItemDto.New.class}) String name, @NotBlank(groups = {ItemDto.New.class}) @Size(max = 500, groups = {ItemDto.New.class, ItemDto.Update.class}) String description, @NotNull(groups = {ItemDto.New.class}) Boolean available, BookingDtoForItemHost lastBooking, BookingDtoForItemHost nextBooking, List<CommentDtoToReturn> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
        this.comments = comments;
    }

    public static ItemDtoWithBookingsAndCommentsBuilder builder() {
        return new ItemDtoWithBookingsAndCommentsBuilder();
    }

    public interface New {
    }

    public interface Exist {
    }

    public interface Update extends ItemDto.Exist {
    }


    private long id;

    @NotBlank(groups = {ItemDto.New.class})
    private String name;

    @NotBlank(groups = {ItemDto.New.class})
    @Size(max = 500, groups = {ItemDto.New.class, ItemDto.Update.class})
    private String description;

    @NotNull(groups = {ItemDto.New.class})
    private Boolean available;

    private BookingDtoForItemHost lastBooking;

    private BookingDtoForItemHost nextBooking;

    private List<CommentDtoToReturn> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDtoWithBookingsAndComments that = (ItemDtoWithBookingsAndComments) o;
        return id == that.id && name.equals(that.name) && description.equals(that.description) && available.equals(that.available) && Objects.equals(lastBooking, that.lastBooking) && Objects.equals(nextBooking, that.nextBooking) && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, available, lastBooking, nextBooking, comments);
    }

    public static class ItemDtoWithBookingsAndCommentsBuilder {
        private long id;
        private @NotBlank(groups = {ItemDto.New.class}) String name;
        private @NotBlank(groups = {ItemDto.New.class}) @Size(max = 500, groups = {ItemDto.New.class, ItemDto.Update.class}) String description;
        private @NotNull(groups = {ItemDto.New.class}) Boolean available;
        private BookingDtoForItemHost lastBooking;
        private BookingDtoForItemHost nextBooking;
        private List<CommentDtoToReturn> comments;

        ItemDtoWithBookingsAndCommentsBuilder() {
        }

        public ItemDtoWithBookingsAndCommentsBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ItemDtoWithBookingsAndCommentsBuilder name(@NotBlank(groups = {ItemDto.New.class}) String name) {
            this.name = name;
            return this;
        }

        public ItemDtoWithBookingsAndCommentsBuilder description(@NotBlank(groups = {ItemDto.New.class}) @Size(max = 500, groups = {ItemDto.New.class, ItemDto.Update.class}) String description) {
            this.description = description;
            return this;
        }

        public ItemDtoWithBookingsAndCommentsBuilder available(@NotNull(groups = {ItemDto.New.class}) Boolean available) {
            this.available = available;
            return this;
        }

        public ItemDtoWithBookingsAndCommentsBuilder lastBooking(BookingDtoForItemHost lastBooking) {
            this.lastBooking = lastBooking;
            return this;
        }

        public ItemDtoWithBookingsAndCommentsBuilder nextBooking(BookingDtoForItemHost nextBooking) {
            this.nextBooking = nextBooking;
            return this;
        }

        public ItemDtoWithBookingsAndCommentsBuilder comments(List<CommentDtoToReturn> comments) {
            this.comments = comments;
            return this;
        }

        public ItemDtoWithBookingsAndComments build() {
            return new ItemDtoWithBookingsAndComments(id, name, description, available, lastBooking, nextBooking, comments);
        }

        public String toString() {
            return "ItemDtoWithBookingsAndComments.ItemDtoWithBookingsAndCommentsBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", available=" + this.available + ", lastBooking=" + this.lastBooking + ", nextBooking=" + this.nextBooking + ", comments=" + this.comments + ")";
        }
    }
}
