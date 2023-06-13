package ru.practicum.shareit.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Size(max = 50)
    private String name;

    @Column
    @Size(max = 50)
    private String email;

    public User() {
    }


    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private long id;
        private String name;
        private String email;

        UserBuilder() {
        }

        public UserBuilder id(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(id, name, email);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", name=" + this.name + ", email=" + this.email + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserBuilder that = (UserBuilder) o;
            return id == that.id && name.equals(that.name) && email.equals(that.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, email);
        }
    }
}
