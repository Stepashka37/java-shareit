package ru.practicum.shareit.request;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "requests")
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestor_id",
            referencedColumnName = "id")
    private User requestor;

    @Column
    private LocalDateTime created = LocalDateTime.now();


    public ItemRequest() {
    }

    public ItemRequest(long id, String description, User requestor, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.requestor = requestor;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRequest that = (ItemRequest) o;
        return id == that.id && description.equals(that.description) && requestor.equals(that.requestor) && created.equals(that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, requestor, created);
    }

    public static ItemRequestBuilder builder() {
        return new ItemRequestBuilder();
    }

    public static class ItemRequestBuilder {
        private long id;
        private String description;
        private User requestor;
        private LocalDateTime created;

        ItemRequestBuilder() {
        }

        public ItemRequestBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ItemRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ItemRequestBuilder requestor(User requestor) {
            this.requestor = requestor;
            return this;
        }

        public ItemRequestBuilder created(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public ItemRequest build() {
            return new ItemRequest(id, description, requestor, created);
        }

        public String toString() {
            return "ItemRequest.ItemRequestBuilder(id=" + this.id + ", description=" + this.description + ", requestor=" + this.requestor + ", created=" + this.created + ")";
        }
    }
}
