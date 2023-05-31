package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

@Query("select u.id, u.name, u.email from " +
        "User as u " +
        "where u.id = ?1")
    List<UserShort> findShortById(long userId);
}
