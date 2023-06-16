package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "comments")
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "item_id",
            referencedColumnName = "id")
    private Item item;

    @OneToOne
    @JoinColumn(name = "author_id",
            referencedColumnName = "id")
    private User user;

    @Column
    private LocalDateTime created;


    public Comment() {

    }

    public Comment(long id, String text, Item item, User user, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.item = item;
        this.user = user;
        this.created = created;
    }

    public Comment(long id, String text, Item item, User user) {
        this.id = id;
        this.text = text;
        this.item = item;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id && text.equals(comment.text) && item.equals(comment.item) && user.equals(comment.user) && created.equals(comment.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, item, user, created);
    }


}
