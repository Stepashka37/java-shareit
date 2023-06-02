package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({UserNotFoundException.class, ItemNotFoundException.class, BookingNotFoundException.class, HostNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFoundExc(final RuntimeException exc) {
        log.error("404: " + exc.getMessage());
        return new ErrorResponse("Object not found", exc.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse anyOtherExc(final Exception exc) {
        log.error("500: " + exc.getMessage());
        return new ErrorResponse("Internal server error", exc.getMessage());
    }

    @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse annotationValidationExc(final RuntimeException exc) {
        log.error("400: " + exc.getMessage());
        return new ErrorResponse("Ошибка валидации с помощью аннотаций", exc.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse annotationValidationExc(final ItemNotAvailableException exc) {
        log.error("400: " + exc.getMessage());
        return new ErrorResponse("Ошибка бронирования - данный предмет уже занят", exc.getMessage());
    }

    @ExceptionHandler(StateValidationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse anyOtherExc(final StateValidationException exc) {
        log.error("500: " + exc.getMessage());
        return new ErrorResponse("Unknown state: UNSUPPORTED_STATUS", exc.getMessage());
    }


}
