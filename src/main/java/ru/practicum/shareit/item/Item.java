package ru.practicum.shareit.item;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "owner_id",
            referencedColumnName = "id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "request_id",
            referencedColumnName = "id")
    private ItemRequest request;


    public Item() {
    }

    public Item(long id, String name, String description, Boolean isAvailable, User owner, ItemRequest request) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isAvailable = isAvailable;
        this.owner = owner;
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && name.equals(item.name) && description.equals(item.description) && isAvailable.equals(item.isAvailable) && Objects.equals(owner, item.owner) && Objects.equals(request, item.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, isAvailable, owner, request);
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public static class ItemBuilder {
        private long id;
        private String name;
        private String description;
        private Boolean isAvailable;
        private User owner;
        private ItemRequest request;

        ItemBuilder() {
        }

        public ItemBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ItemBuilder isAvailable(Boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public ItemBuilder owner(User owner) {
            this.owner = owner;
            return this;
        }

        public ItemBuilder request(ItemRequest request) {
            this.request = request;
            return this;
        }

        public Item build() {
            return new Item(id, name, description, isAvailable, owner, request);
        }

        public String toString() {
            return "Item.ItemBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", isAvailable=" + this.isAvailable + ", owner=" + this.owner + ", request=" + this.request + ")";
        }
    }
}
