package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("update Booking " +
            "set status= ?1 " +
            "where id = ?2  ")
    @Modifying
    void updateStatus(BookingStatus status, long id);

    List<Booking> findAllByBookerId(long bookerId, Sort sort);

    @Query("select b from Booking as b " +
            "join b.booker as u  " +
            "where b.booker.id = ?1  and ?2 " +
            "between b.start  and b.end  " +
            "order by b.start desc")
    List<Booking> findAllCurrentBookingsByUser(long id, LocalDateTime dateTime, Sort sort);

    @Query("select b from Booking as b " +
            "join b.booker as u  " +
            "where b.booker.id = ?1  and b.start > ?2 " +
            "order by b.start desc")
    List<Booking> findAllFutureBookingsByUser(long userId, LocalDateTime dateTime, Sort sort);

    @Query("select b from Booking as b " +
            "join b.booker as u  " +
            "where b.booker.id = ?1  and b.end < ?2 " +
            "order by b.start desc")
    List<Booking> findAllPastBookingsByUser(long userId, LocalDateTime dateTime, Sort sort);

    @Query("select b from Booking as b " +
            "join b.booker as u  " +
            "where b.booker.id = ?1  and b.status = 'REJECTED'" +
            "order by b.start desc")
    List<Booking> findAllRejectedBookingsByUser(long userId, Sort sort);

    @Query("select b from Booking as b " +
            "join b.booker as u  " +
            "where b.booker.id = ?1  and b.status = 'WAITING'" +
            "order by b.start desc")
    List<Booking> findAllWaitingBookingsByUser(long userId, Sort sort);

    @Query("select b from Booking as b " +
            "join b.item as i " +
            "where i.id in :userItems")
    List<Booking> findAllItemsBookings(List userItems, Sort sort);

    @Query("select b from Booking as b " +
            "join b.item as i " +
            "where i.id in ?1 " +
            "and ?2 between b.start  and b.end ")
    List<Booking> findAllItemsCurrentBookings(List userItems, LocalDateTime dateTime, Sort sort);

    @Query("select b from Booking as b " +
            "join b.item as i " +
            "where i.id in ?1 " +
            "and b.end < ?2 ")
    List<Booking> findAllItemsPastBookings(List userItems, LocalDateTime dateTime, Sort sort);

    @Query("select b from Booking as b " +
            "join b.item as i " +
            "where i.id in ?1 " +
            "and b.start > ?2 ")
    List<Booking> findAllItemsFutureBookings(List userItems, LocalDateTime dateTime, Sort sort);

    @Query("select b from Booking as b " +
            "join b.item as i " +
            "where i.id in ?1 " +
            "and b.status = 'REJECTED' ")
    List<Booking> findAllItemsRejectedBookings(List userItems, Sort sort);

    @Query("select b from Booking as b " +
            "join b.item as i " +
            "where i.id in ?1 " +
            "and b.status = 'WAITING' ")
    List<Booking> findAllItemsWaitingBookings(List userItems, Sort sort);

    @Query("select b from Booking as b " +
            "join b.item as i " +
            "where i.id = ?1 and " +
            "b.status = 'APPROVED'")
    List<Booking> findAllBookingsByItemId(long itemId);

    @Query("select b from Booking as b " +
            "join b.item as i " +
            "join b.booker as u " +
            "where i.id = ?1 " +
            "and u.id = ?2 " +
            "and b.end < ?3 ")
    List<Booking> findAllByBookerIdAndItemIdAndEndBefore(long itemId, long bookerId, LocalDateTime end);

}
