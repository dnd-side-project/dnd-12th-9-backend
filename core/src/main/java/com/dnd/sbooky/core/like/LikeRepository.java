package com.dnd.sbooky.core.like;

import com.dnd.sbooky.core.LockTimeout;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @LockTimeout
    @Query("select l from LikeEntity l where l.id = :id")
    Optional<LikeEntity> findByIdWithPessimisticLock(Long id);
}
