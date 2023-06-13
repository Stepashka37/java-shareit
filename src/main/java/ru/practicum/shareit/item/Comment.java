package ru.practicum.shareit.item;

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

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public static class CommentBuilder {
        private long id;
        private String text;
        private Item item;
        private User user;
        private LocalDateTime created;

        CommentBuilder() {
        }

        public CommentBuilder id(long id) {
            this.id = id;
            return this;
        }

        public CommentBuilder text(String text) {
            this.text = text;
            return this;
        }

        public CommentBuilder item(Item item) {
            this.item = item;
            return this;
        }

        public CommentBuilder user(User user) {
            this.user = user;
            return this;
        }

        public CommentBuilder created(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public Comment build() {
            return new Comment(id, text, item, user, created);
        }

        public String toString() {
            return "Comment.CommentBuilder(id=" + this.id + ", text=" + this.text + ", item=" + this.item + ", user=" + this.user + ", created=" + this.created + ")";
        }
    }
}
