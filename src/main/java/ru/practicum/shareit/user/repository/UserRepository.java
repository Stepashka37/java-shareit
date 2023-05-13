package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {

    User getUserById(long userId);

    List<User> getAllUsers();

    User addNewUser(User user);

    User updateUser(long userId, User user);

    void deleteUserById(long userId);

    void deleteAllUsers();
}
