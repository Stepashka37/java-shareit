package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.*;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {

    public interface New {
    }

    public interface Exist{
    }

    public interface Update extends Exist {
    }


    private long id;

    @NotBlank(groups = {New.class})
    private String name;

    @NotBlank(groups = {New.class})
    @Size(max = 500, groups = {New.class, Update.class})
    private  String description;

    @NotNull(groups = {New.class})
    private Boolean available;



}
