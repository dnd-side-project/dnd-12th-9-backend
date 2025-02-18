package com.dnd.sbooky.core.item;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query("select i.id from ItemEntity i")
    List<Long> findAllItemIds();
}
