package ru.practicum.shareit.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private  UserMapper userMapper;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable long userId) {
        User fromService = userService.getUserById(userId);
        return userMapper.modelToDto(fromService);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(x -> userMapper.modelToDto(x))
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDto createUser(@Validated(UserDto.New.class) @RequestBody UserDto userDto) {
        User userFromRequest = userMapper.dtoToModel(userDto);
        User userCreated = userService.createNewUser(userFromRequest);
        UserDto userDtoFromService = userMapper.modelToDto(userCreated);

        return userDtoFromService;
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable long userId, @Validated(UserDto.Update.class) @RequestBody UserDto userDto) {
        User userFromRequest = userMapper.dtoToModel(userDto);
        User userUpdated = userService.updateUser(userId, userFromRequest);
        UserDto userDtoFromService = userMapper.modelToDto(userUpdated);
        System.out.println(userDtoFromService);
        return userDtoFromService;
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable long userId) {
        userService.deleteUserById(userId);
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userService.deleteUsers();
    }

}
