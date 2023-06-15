package ru.practicum.shareit.request;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequestMapping(path = "/requests")
@Validated
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ItemRequestDto createRequest(@RequestHeader("X-Sharer-User-id") long userId,
                                        @Validated @RequestBody ItemRequestDtoToCreate itemRequestDtoToCreate) {
        return itemRequestService.createRequest(userId, itemRequestDtoToCreate);
    }

    @GetMapping
    public List<ItemRequestDto> getRequests(@RequestHeader("X-Sharer-User-id") long userId) {
        return itemRequestService.getUserRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getOtherUsersRequests(@RequestHeader("X-Sharer-User-id") long userId,
                                                      @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
                                                      @RequestParam(value = "size", defaultValue = "1") @Min(1) Integer size) {
        return itemRequestService.getOtherUsersRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@RequestHeader("X-Sharer-User-id") long userId,
                                         @PathVariable long requestId) {
        return itemRequestService.getRequestById(userId, requestId);
    }
}
