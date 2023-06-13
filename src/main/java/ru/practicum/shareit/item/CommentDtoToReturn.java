package ru.practicum.shareit.item;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDtoToReturn that = (CommentDtoToReturn) o;
        return id == that.id && text.equals(that.text) && authorName.equals(that.authorName) && created.equals(that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, authorName, created);
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
