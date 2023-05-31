package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
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
    public UserDto getUserById(long userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserDto createNewUser(UserDto userDto) {
        UserDto userCreated = userRepository.addNewUser(userDto);
        log.info("Создали пользователя с id{}", userCreated.getId());
        return userCreated;
    }

    @Override
    public UserDto updateUser(long userId, UserDto userDto) {
        UserDto userUpdated = userRepository.updateUser(userId, userDto);
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
