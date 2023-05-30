package ru.practicum.shareit.user.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;


@Data
@Builder
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column
    private  String name;

    @Column
    private  String email;

    /*@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Item> items;*/

    public User() {
    }


    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
