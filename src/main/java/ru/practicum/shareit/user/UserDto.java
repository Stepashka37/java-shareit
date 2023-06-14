package ru.practicum.shareit.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDto {

    UserDto(long id, @NotBlank(groups = {New.class}) String name, @NotBlank(groups = {New.class}) @Email(groups = {New.class, Update.class}) String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

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


    public static class UserDtoBuilder {
        private long id;
        private @NotBlank(groups = {New.class}) String name;
        private @NotBlank(groups = {New.class}) @Email(groups = {New.class, Update.class}) String email;

        UserDtoBuilder() {
        }

        public UserDtoBuilder id(long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder name(@NotBlank(groups = {New.class}) String name) {
            this.name = name;
            return this;
        }

        public UserDtoBuilder email(@NotBlank(groups = {New.class}) @Email(groups = {New.class, Update.class}) String email) {
            this.email = email;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, name, email);
        }

        public String toString() {
            return "UserDto.UserDtoBuilder(id=" + this.id + ", name=" + this.name + ", email=" + this.email + ")";
        }
    }
}
