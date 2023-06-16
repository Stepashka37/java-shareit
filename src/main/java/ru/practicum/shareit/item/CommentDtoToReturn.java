package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
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

}
