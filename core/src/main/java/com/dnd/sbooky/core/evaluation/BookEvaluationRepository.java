package com.dnd.sbooky.core.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookEvaluationRepository extends JpaRepository<BookEvaluationEntity, Long> {

    @Modifying
    @Query("DELETE FROM BookEvaluationEntity be WHERE be.memberBook.id = :memberBookId")
    void deleteAllByMemberBookId(@Param("memberBookId") Long memberBookId);
}
