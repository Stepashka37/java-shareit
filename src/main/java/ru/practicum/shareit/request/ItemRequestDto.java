package ru.practicum.shareit.request;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class ItemRequestDto {

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotNull
    private User requestor;

    private LocalDateTime created;

    ItemRequestDto(@NotBlank @Size(max = 500) String description, @NotNull User requestor, LocalDateTime created) {
        this.description = description;
        this.requestor = requestor;
        this.created = created;
    }

    public static ItemRequestDtoBuilder builder() {
        return new ItemRequestDtoBuilder();
    }

    public static class ItemRequestDtoBuilder {
        private @NotBlank @Size(max = 500) String description;
        private @NotNull User requestor;
        private LocalDateTime created;

        ItemRequestDtoBuilder() {
        }

        public ItemRequestDtoBuilder description(@NotBlank @Size(max = 500) String description) {
            this.description = description;
            return this;
        }

        public ItemRequestDtoBuilder requestor(@NotNull User requestor) {
            this.requestor = requestor;
            return this;
        }

        public ItemRequestDtoBuilder created(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public ItemRequestDto build() {
            return new ItemRequestDto(description, requestor, created);
        }

        public String toString() {
            return "ItemRequestDto.ItemRequestDtoBuilder(description=" + this.description + ", requestor=" + this.requestor + ", created=" + this.created + ")";
        }
    }
}
