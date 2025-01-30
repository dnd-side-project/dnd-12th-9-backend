package com.dnd.sbooky.core.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBookRepository extends JpaRepository<MemberBookEntity, Long>, MemberBookRepositoryCustom {
}
