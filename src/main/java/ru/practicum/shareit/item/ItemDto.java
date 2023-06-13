package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


@Getter
@Setter
@Builder
public class ItemDto {

    public ItemDto() {
    }

    ItemDto(long id, @NotBlank(groups = {New.class}) String name, @NotBlank(groups = {New.class}) @Size(max = 500, groups = {New.class, Update.class}) String description, @NotNull(groups = {New.class}) Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public ItemDto(long id, String name, String description, Boolean available, long requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }

    public static ItemDtoBuilder builder() {
        return new ItemDtoBuilder();
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
    @Size(max = 500, groups = {New.class, Update.class})
    private String description;

    @NotNull(groups = {New.class})
    private Boolean available;

    private long requestId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return id == itemDto.id && name.equals(itemDto.name) && description.equals(itemDto.description) && available.equals(itemDto.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, available);
    }

    public static class ItemDtoBuilder {
        private long id;
        private @NotBlank(groups = {New.class}) String name;
        private @NotBlank(groups = {New.class}) @Size(max = 500, groups = {New.class, Update.class}) String description;
        private @NotNull(groups = {New.class}) Boolean available;

        ItemDtoBuilder() {
        }

        public ItemDtoBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ItemDtoBuilder name(@NotBlank(groups = {New.class}) String name) {
            this.name = name;
            return this;
        }

        public ItemDtoBuilder description(@NotBlank(groups = {New.class}) @Size(max = 500, groups = {New.class, Update.class}) String description) {
            this.description = description;
            return this;
        }

        public ItemDtoBuilder available(@NotNull(groups = {New.class}) Boolean available) {
            this.available = available;
            return this;
        }

        public ItemDto build() {
            return new ItemDto(id, name, description, available);
        }

        public String toString() {
            return "ItemDto.ItemDtoBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", available=" + this.available + ")";
        }
    }
}
