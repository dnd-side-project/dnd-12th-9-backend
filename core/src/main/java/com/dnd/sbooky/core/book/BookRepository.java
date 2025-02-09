package com.dnd.sbooky.core.book;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByAuthorAndTitle(String author, String title);
}
