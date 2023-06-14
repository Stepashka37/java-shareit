package ru.practicum.shareit.request;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public ItemRequestDto modelToDto(ItemRequest request) {
        return ItemRequestDto.builder()
                .description(request.getDescription())
                .requestor(request.getRequestor())
                .created(request.getCreated())
                .build();
    }

    public ItemRequest dtoToModel(ItemRequestDto requestDto) {
        return ItemRequest.builder()
                .description(requestDto.getDescription())
                .requestor(requestDto.getRequestor())
                .created(requestDto.getCreated())
                .build();
    }
}
