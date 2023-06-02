package ru.practicum.shareit.item;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDtoToReturn {

    private long id;

    private String text;

    private String authorName;

    private LocalDateTime created;

    CommentDtoToReturn(long id, String text, String authorName, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.authorName = authorName;
        this.created = created;
    }

    public static CommentDtoToReturnBuilder builder() {
        return new CommentDtoToReturnBuilder();
    }

    public static class CommentDtoToReturnBuilder {
        private long id;
        private String text;
        private String authorName;
        private LocalDateTime created;

        CommentDtoToReturnBuilder() {
        }

        public CommentDtoToReturnBuilder id(long id) {
            this.id = id;
            return this;
        }

        public CommentDtoToReturnBuilder text(String text) {
            this.text = text;
            return this;
        }

        public CommentDtoToReturnBuilder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public CommentDtoToReturnBuilder created(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public CommentDtoToReturn build() {
            return new CommentDtoToReturn(id, text, authorName, created);
        }

        public String toString() {
            return "CommentDtoToReturn.CommentDtoToReturnBuilder(id=" + this.id + ", text=" + this.text + ", authorName=" + this.authorName + ", created=" + this.created + ")";
        }
    }
}
