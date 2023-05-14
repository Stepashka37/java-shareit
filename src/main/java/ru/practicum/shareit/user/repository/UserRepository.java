package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserRepository {

    UserDto getUserById(long userId);

    List<UserDto> getAllUsers();

    UserDto addNewUser(UserDto userDto);

    UserDto updateUser(long userId, UserDto userDto);

    void deleteUserById(long userId);

    void deleteAllUsers();
}
