package ru.practicum.shareit.item;

import lombok.Builder;
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
@Builder
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

}
