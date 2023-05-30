/*
package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.dto.mapper.UserMapper.dtoToModel;
import static ru.practicum.shareit.user.dto.mapper.UserMapper.modelToDto;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private List<User> users = new ArrayList<>();
    private long userId = 1;

    @Override
    public UserDto getUserById(long userId) {

        return users.stream().filter(user -> user.getId() == userId).findAny()
                .map(x -> modelToDto(x))
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id" + userId + "не найден"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return users.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        User user = dtoToModel(userDto);
        if (users.stream().filter(x -> x.getEmail().equals(user.getEmail())).findAny().isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }
        user.setId(userId);
        users.add(user);
        User toReturn = users.stream().filter(x -> x.getId() == userId).findAny().get();
        userId++;
        return modelToDto(toReturn);
    }

    @Override
    public UserDto updateUser(long userId, UserDto userDto) {
        User user = dtoToModel(userDto);
        if (users.stream()
                .filter(x -> x.getId() != userId)
                .filter(x -> x.getEmail().equals(user.getEmail()))
                .findAny()
                .isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        User userFromList = users.stream().filter(x -> x.getId() == userId).findAny()
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id" + userId + "не найден"));
        if (fieldIsValid(user.getName()) && fieldIsValid(user.getEmail())) {
            userFromList.setName(user.getName());
            userFromList.setEmail(user.getEmail());
            users.removeIf(x -> x.getId() == userId);
            users.add(userFromList);
        } else if (fieldIsValid(user.getEmail()) && !fieldIsValid(user.getName())) {
            userFromList.setEmail(user.getEmail());
            users.removeIf(x -> x.getId() == userId);
            users.add(userFromList);
        } else if (!fieldIsValid(user.getEmail()) && fieldIsValid(user.getName())) {
            userFromList.setName(user.getName());
            users.removeIf(x -> x.getId() == userId);
            users.add(userFromList);
        }

        return modelToDto(userFromList);
    }


    @Override
    public void deleteUserById(long userId) {
        if (users.stream().filter(x -> x.getId() == userId).findAny() != null) {
            users.removeIf(x -> x.getId() == userId);
        } else {
            throw new UserNotFoundException("Пользователь с id" + userId + "не найден");
        }
    }

    @Override
    public void deleteAllUsers() {
        users.clear();
    }

    private  boolean fieldIsValid(String field) {
        return field != null && !field.isBlank();
    }
}
*/
