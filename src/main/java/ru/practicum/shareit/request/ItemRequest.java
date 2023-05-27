package ru.practicum.shareit.request;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Builder
@Entity
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id = 1;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "requestor_id",
    referencedColumnName = "id")
    private User requestor;

    private LocalDateTime created;

    public ItemRequest() {
    }

    public ItemRequest(String description, User requestor, LocalDateTime created) {
        this.description = description;
        this.requestor = requestor;
        this.created = created;
    }
}
