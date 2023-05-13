package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    User getUserById(long userId);

    List<User> getAllUsers();

    User createNewUser(User user);

    User updateUser(long userId, User user);

    void deleteUserById(long userId);

    void deleteUsers();
}
