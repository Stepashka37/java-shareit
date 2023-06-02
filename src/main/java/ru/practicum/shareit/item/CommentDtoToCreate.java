package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDtoToCreate {

    @NotBlank
    @Size(max = 1000)
    private String text;

    public static CommentDtoToCreateBuilder builder() {
        return new CommentDtoToCreateBuilder();
    }


    public static class CommentDtoToCreateBuilder {
        private @NotBlank @Size(max = 1000) String text;

        CommentDtoToCreateBuilder() {
        }

        public CommentDtoToCreateBuilder text(@NotBlank @Size(max = 1000) String text) {
            this.text = text;
            return this;
        }

        public CommentDtoToCreate build() {
            return new CommentDtoToCreate(text);
        }

        public String toString() {
            return "CommentDtoToCreate.CommentDtoToCreateBuilder(text=" + this.text + ")";
        }
    }
}
