package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@ActiveProfiles(profiles = {"ci,test"})
@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
@Transactional
class BookingRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BookingRepository underTest;

    @Test
    void itShouldBeInitialized() {
        // Given
        // When
        // Then
        assertThat(underTest).isNotNull();

    }

    @Test
    void itShouldSaveBooking() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking = Booking.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusMinutes(30))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item)
                .build();

        userRepository.save(owner);
        userRepository.save(booker);
        itemRepository.save(item);
        // When
        Booking bookingCreated = underTest.save(booking);
        // Then
        assertThat(bookingCreated).isEqualTo(booking);

    }

    @Test
    void itShouldNotSaveBookingWhenStartNull() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(null)
                .end(LocalDateTime.now().plusMinutes(30))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item)
                .build();

        userRepository.save(owner);
        userRepository.save(booker);
        itemRepository.save(item);
        // When
        // Then
        assertThatThrownBy(() -> underTest.save(booking1))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void itShouldNotSaveBookingWhenEndNull() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now())
                .end(null)
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item)
                .build();

        userRepository.save(owner);
        userRepository.save(booker);
        itemRepository.save(item);
        // When
        // Then
        assertThatThrownBy(() -> underTest.save(booking1))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void itShouldNotSaveBookingWhenStatusNull() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusMinutes(30))
                .status(null)
                .booker(booker)
                .item(item)
                .build();

        userRepository.save(owner);
        userRepository.save(booker);
        itemRepository.save(item);
        // When
        // Then
        assertThatThrownBy(() -> underTest.save(booking1))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void itShouldNotSaveBookingWhenBookerNull() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusMinutes(30))
                .status(BookingStatus.WAITING)
                .booker(null)
                .item(item)
                .build();

        userRepository.save(owner);
        userRepository.save(booker);
        itemRepository.save(item);
        // When
        // Then
        assertThatThrownBy(() -> underTest.save(booking1))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void itShouldNotSaveBookingWhenItemNull() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusMinutes(30))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(null)
                .build();

        userRepository.save(owner);
        userRepository.save(booker);
        itemRepository.save(item);
        // When
        // Then
        assertThatThrownBy(() -> underTest.save(booking1))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void itShouldFindAllByBookerId() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User owner2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Item item2 = Item.builder()
                .name("Item2")
                .description("Item2 description")
                .isAvailable(true)
                .owner(owner2)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusMinutes(30))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item2)
                .build();

        userRepository.save(owner);
        userRepository.save(owner2);
        itemRepository.save(item);
        itemRepository.save(item2);
        userRepository.save(booker);

        // When
        underTest.save(booking1);
        underTest.save(booking2);
        // Then
        assertThat(underTest.findAllByBookerIdOrderByStartDesc(booker.getId()))
                .hasSize(2)
                .containsExactly(booking2, booking1);

    }

    @Test
    void itShouldFindAllCurrentBookings() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User owner2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Item item2 = Item.builder()
                .name("Item2")
                .description("Item2 description")
                .isAvailable(true)
                .owner(owner2)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now().minusMinutes(10))
                .end(LocalDateTime.now().plusMinutes(30))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item2)
                .build();

        userRepository.save(owner);
        userRepository.save(owner2);
        itemRepository.save(item);
        itemRepository.save(item2);
        userRepository.save(booker);

        // When
        underTest.save(booking1);
        underTest.save(booking2);
        // Then
        assertThat(underTest.findAllCurrentBookingsByUser(booker.getId(), LocalDateTime.now()))
                .hasSize(1)
                .containsExactly(booking1);

    }

    @Test
    void itShouldFindAllFutureBookings() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User owner2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Item item2 = Item.builder()
                .name("Item2")
                .description("Item2 description")
                .isAvailable(true)
                .owner(owner2)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now().minusMinutes(10))
                .end(LocalDateTime.now().plusMinutes(30))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item2)
                .build();

        userRepository.save(owner);
        userRepository.save(owner2);
        itemRepository.save(item);
        itemRepository.save(item2);
        userRepository.save(booker);

        // When
        underTest.save(booking1);
        underTest.save(booking2);
        // Then
        assertThat(underTest.findAllFutureBookingsByUser(booker.getId(), LocalDateTime.now()))
                .hasSize(1)
                .containsExactly(booking2);

    }

    @Test
    void itShouldFindAllPastBookings() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User owner2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Item item2 = Item.builder()
                .name("Item2")
                .description("Item2 description")
                .isAvailable(true)
                .owner(owner2)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now().minusMinutes(40))
                .end(LocalDateTime.now().minusMinutes(10))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item2)
                .build();

        userRepository.save(owner);
        userRepository.save(owner2);
        itemRepository.save(item);
        itemRepository.save(item2);
        userRepository.save(booker);

        // When
        underTest.save(booking1);
        underTest.save(booking2);
        // Then
        assertThat(underTest.findAllPastBookingsByUser(booker.getId(), LocalDateTime.now()))
                .hasSize(1)
                .containsExactly(booking1);

    }

    @Test
    void itShouldFindAllRejectedAndWaitingBookings() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User owner2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Item item2 = Item.builder()
                .name("Item2")
                .description("Item2 description")
                .isAvailable(true)
                .owner(owner2)
                .build();

        User booker = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now().minusMinutes(40))
                .end(LocalDateTime.now().minusMinutes(10))
                .status(BookingStatus.WAITING)
                .booker(booker)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.REJECTED)
                .booker(booker)
                .item(item2)
                .build();

        userRepository.save(owner);
        userRepository.save(owner2);
        itemRepository.save(item);
        itemRepository.save(item2);
        userRepository.save(booker);

        // When
        underTest.save(booking1);
        underTest.save(booking2);
        // Then
        assertThat(underTest.findAllRejectedBookingsByUser(booker.getId()))
                .hasSize(1)
                .containsExactly(booking2);

        assertThat(underTest.findAllWaitingBookingsByUser(booker.getId()))
                .hasSize(1)
                .containsExactly(booking1);

    }

    @Test
    void itShouldFindAllItemsBookings() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();


        Item item2 = Item.builder()
                .name("Item2")
                .description("Item2 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User booker1 = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        User booker2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now().minusMinutes(40))
                .end(LocalDateTime.now().minusMinutes(10))
                .status(BookingStatus.WAITING)
                .booker(booker1)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.WAITING)
                .booker(booker2)
                .item(item2)
                .build();

        userRepository.save(owner);
        userRepository.save(booker1);
        userRepository.save(booker2);
        itemRepository.save(item);
        itemRepository.save(item2);


        // When
        underTest.save(booking1);
        underTest.save(booking2);
        List<Long> userItems = itemRepository.findAllByOwnerIdOrderByIdAsc(owner.getId())
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        // Then
        assertThat(underTest.findAllItemsBookings(userItems))
                .hasSize(2)
                .contains(booking2, booking1);

    }

    @Test
    void itShouldFindAllCurrentItemsBookings() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();


        Item item2 = Item.builder()
                .name("Item2")
                .description("Item2 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User booker1 = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        User booker2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now().minusMinutes(40))
                .end(LocalDateTime.now().plusMinutes(40))
                .status(BookingStatus.WAITING)
                .booker(booker1)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.WAITING)
                .booker(booker2)
                .item(item2)
                .build();

        userRepository.save(owner);
        userRepository.save(booker1);
        userRepository.save(booker2);
        itemRepository.save(item);
        itemRepository.save(item2);


        // When
        underTest.save(booking1);
        underTest.save(booking2);
        List<Long> userItems = itemRepository.findAllByOwnerIdOrderByIdAsc(owner.getId())
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        // Then
        assertThat(underTest.findAllItemsCurrentBookings(userItems, LocalDateTime.now()))
                .hasSize(1)
                .contains(booking1);

    }

    @Test
    void itShouldFindAllPastAndFutureItemsBookings() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();


        Item item2 = Item.builder()
                .name("Item2")
                .description("Item2 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User booker1 = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        User booker2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now().minusMinutes(40))
                .end(LocalDateTime.now().minusMinutes(10))
                .status(BookingStatus.WAITING)
                .booker(booker1)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.WAITING)
                .booker(booker2)
                .item(item2)
                .build();

        userRepository.save(owner);
        userRepository.save(booker1);
        userRepository.save(booker2);
        itemRepository.save(item);
        itemRepository.save(item2);


        // When
        underTest.save(booking1);
        underTest.save(booking2);
        List<Long> userItems = itemRepository.findAllByOwnerIdOrderByIdAsc(owner.getId())
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        // Then
        assertThat(underTest.findAllItemsPastBookings(userItems, LocalDateTime.now()))
                .hasSize(1)
                .contains(booking1);

        assertThat(underTest.findAllItemsFutureBookings(userItems, LocalDateTime.now()))
                .hasSize(1)
                .contains(booking2);
    }

    @Test
    void itShouldFindAllRejectedAndWaitingItemsBookings() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();


        Item item2 = Item.builder()
                .name("Item2")
                .description("Item2 description")
                .isAvailable(true)
                .owner(owner)
                .build();

        User booker1 = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        User booker2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now().minusMinutes(40))
                .end(LocalDateTime.now().minusMinutes(10))
                .status(BookingStatus.WAITING)
                .booker(booker1)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.REJECTED)
                .booker(booker2)
                .item(item2)
                .build();

        userRepository.save(owner);
        userRepository.save(booker1);
        userRepository.save(booker2);
        itemRepository.save(item);
        itemRepository.save(item2);


        // When
        underTest.save(booking1);
        underTest.save(booking2);
        List<Long> userItems = itemRepository.findAllByOwnerIdOrderByIdAsc(owner.getId())
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        // Then
        assertThat(underTest.findAllItemsRejectedBookings(userItems))
                .hasSize(1)
                .contains(booking2);

        assertThat(underTest.findAllItemsWaitingBookings(userItems))
                .hasSize(1)
                .contains(booking1);
    }

    @Test
    void itShouldFindAllBookingsByItemId() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();


        User booker1 = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        User booker2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now().minusMinutes(40))
                .end(LocalDateTime.now().minusMinutes(10))
                .status(BookingStatus.APPROVED)
                .booker(booker1)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.APPROVED)
                .booker(booker2)
                .item(item)
                .build();

        userRepository.save(owner);
        userRepository.save(booker1);
        userRepository.save(booker2);
        itemRepository.save(item);


        // When
        underTest.save(booking1);
        underTest.save(booking2);
        // Then
        assertThat(underTest.findAllBookingsByItemId(item.getId()))
                .hasSize(2)
                .containsExactly(booking2, booking1);


    }

    @Test
    void itShouldFindAllByBookerIdAndItemIdEndBefore() {
        // Given
        User owner = User.builder()
                .name("Username")
                .email("Useremail@yandex.ru")
                .build();

        Item item = Item.builder()
                .name("Item1")
                .description("Item1 description")
                .isAvailable(true)
                .owner(owner)
                .build();


        User booker1 = User.builder()
                .name("Username")
                .email("Useremail1@yandex.ru")
                .build();

        User booker2 = User.builder()
                .name("Username")
                .email("Useremail2@yandex.ru")
                .build();

        Booking booking1 = Booking.builder()
                .start(LocalDateTime.now().minusMinutes(40))
                .end(LocalDateTime.now().minusMinutes(10))
                .status(BookingStatus.APPROVED)
                .booker(booker1)
                .item(item)
                .build();

        Booking booking2 = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(30))
                .end(LocalDateTime.now().plusMinutes(60))
                .status(BookingStatus.APPROVED)
                .booker(booker2)
                .item(item)
                .build();

        userRepository.save(owner);
        userRepository.save(booker1);
        userRepository.save(booker2);
        itemRepository.save(item);


        // When
        underTest.save(booking1);
        underTest.save(booking2);
        // Then
        assertThat(underTest.findAllByBookerIdAndItemIdAndEndBefore(item.getId(), booker1.getId(), LocalDateTime.now()))
                .hasSize(1)
                .containsExactly(booking1);


    }

}