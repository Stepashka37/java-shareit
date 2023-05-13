package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User createNewUser(User user) {
        User userCreated = userRepository.addNewUser(user);
        log.info("Создали пользователя с id{}", userCreated.getId());
        return userCreated;
    }

    @Override
    public User updateUser(long userId, User user) {
        User userUpdated = userRepository.updateUser(userId, user);
        log.info("Обновили данные пользователя с id{}", userUpdated.getId());
        return userUpdated;
    }

    @Override
    public void deleteUserById(long userId) {
        userRepository.deleteUserById(userId);
        log.info("Удалили пользователя с id{}", userId);
    }

    @Override
    public void deleteUsers() {
        userRepository.deleteAllUsers();
        log.info("Удалили всех пользователей");
    }
}
