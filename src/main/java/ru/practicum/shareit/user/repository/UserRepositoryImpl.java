package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private List<User> users = new ArrayList<>();
    private long userId = 1;

    @Override
    public User getUserById(long userId) {

        return users.stream().filter(user -> user.getId() == userId).findAny()
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id" + userId + "не найден"));
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User addNewUser(User user) {
        if(users.stream().filter(x -> x.getEmail().equals(user.getEmail())).findAny().isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }
        user.setId(userId);
        users.add(user);
        User toReturn = users.stream().filter(x -> x.getId() == userId).findAny().get();
        userId++;
        return toReturn;
    }

    @Override
    public User updateUser(long userId, User user) {

        if(users.stream()
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

        return userFromList;
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
