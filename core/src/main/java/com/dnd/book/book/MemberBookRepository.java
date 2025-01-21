package com.dnd.book.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBookRepository extends JpaRepository<MemberBookEntity, Long> {
}
