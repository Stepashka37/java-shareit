package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByOwnerIdOrderByIdAsc(long userId);

    @Query("select it " +
            "from Item as it " +
            "where ((lower(it.name) like %:text% " +
            "or lower(it.description) like %:text%)" +
            "and it.isAvailable is true) ")
    List<Item> findAllByText(@Param("text") String text);

    List<Item> findAllByRequestId(long requestId);

}
