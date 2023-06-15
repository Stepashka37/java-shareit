package ru.practicum.shareit.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.RequestNotFound;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.ItemMapper.modelToDto;
import static ru.practicum.shareit.request.RequestMapper.dtoToModel;
import static ru.practicum.shareit.request.RequestMapper.modelToDto;

@Service
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRequestRepository = itemRequestRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ItemRequestDto createRequest(long userId, ItemRequestDtoToCreate itemRequestDtoToCreate) {
        User userFound = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        ItemRequest itemRequestToSave = dtoToModel(itemRequestDtoToCreate);
        itemRequestToSave.setRequestor(userFound);
        ItemRequest requestSaved = itemRequestRepository.save(itemRequestToSave);
        log.info("Создали запрос с id{}", requestSaved.getId());
        return modelToDto(requestSaved);
    }

    @Override
    public List<ItemRequestDto> getUserRequests(long userId) {
        User userFound = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<ItemRequestDto> itemRequests = itemRequestRepository.findAllByRequestorIdOrderByCreatedAsc(userId)
                .stream()
                .map(x -> modelToDto(x))
                .peek(t -> t.setItems(itemRepository.findAllByRequestId(t.getId())
                        .stream()
                        .map(y -> modelToDto(y))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
        log.info("Получили список всех запросов пользователя с id{}", userId);
        return itemRequests;

    }

    @Override
    public List<ItemRequestDto> getOtherUsersRequests(long userId, int from, int size) {
        User userFound = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").ascending());
        Page<ItemRequest> pagedResult = itemRequestRepository.findAllUserRequest(userId, pageable);
        List<ItemRequestDto> usersRequests = new ArrayList<>();
        if (pagedResult.hasContent()) {
           usersRequests =  pagedResult.stream()
                    .map(x -> modelToDto(x))
                   .peek(t -> t.setItems(itemRepository.findAllByRequestId(t.getId())
                           .stream()
                           .map(y -> modelToDto(y))
                           .collect(Collectors.toList())))
                    .collect(Collectors.toList());
        }
        log.info("Получили список всех запросов пользователей начиная со страницы {page}", page);
        return usersRequests;
    }

    @Override
    public ItemRequestDto getRequestById(long userId, long requestId) {
        User userFound = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFound("Request not found"));
        List<ItemDto> items = itemRepository.findAllByRequestId(itemRequest.getId())
                        .stream()
                        .map(x -> modelToDto(x))
                        .collect(Collectors.toList());
        ItemRequestDto result = modelToDto(itemRequest);
        result.setItems(items);
        log.info("Получили данные о запросе с id{}", requestId);
        return result;
    }


}
