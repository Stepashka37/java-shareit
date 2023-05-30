package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDtoForItemHost;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class ItemDtoWithBookings {
    public interface New {
    }

    public interface Exist{
    }

    public interface Update extends ItemDto.Exist {
    }


    private long id;

    @NotBlank(groups = {ItemDto.New.class})
    private String name;

    @NotBlank(groups = {ItemDto.New.class})
    @Size(max = 500, groups = {ItemDto.New.class, ItemDto.Update.class})
    private  String description;

    @NotNull(groups = {ItemDto.New.class})
    private Boolean available;

    private BookingDtoForItemHost lastBooking;

    private BookingDtoForItemHost nextBooking;
}
