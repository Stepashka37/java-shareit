package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(long userId);

    List<UserDto> getAllUsers();

    UserDto createNewUser(UserDto userDto);

    UserDto updateUser(long userId, UserDto userDto);

    void deleteUserById(long userId);

    void deleteUsers();
}
