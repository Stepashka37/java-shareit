package ru.practicum.shareit.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

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
    @Size(max = 50)
    private String name;

    @NotBlank(groups = {New.class})
    @Email(groups = {New.class, Update.class})
    @Size(max = 50)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id && name.equals(userDto.name) && email.equals(userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
