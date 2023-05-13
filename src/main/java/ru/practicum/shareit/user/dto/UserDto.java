package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDto {

    public interface New {
    }

    public interface Exist {
    }

    public interface Update extends Exist {
    }


    private long id;

    @NotBlank(groups = {New.class})
    private String name;

    @NotBlank(groups = {New.class})
    @Email(groups = {New.class, Update.class})
    private String email;


}
