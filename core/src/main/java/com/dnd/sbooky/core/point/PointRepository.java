package com.dnd.sbooky.core.point;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRepository extends JpaRepository<PointEntity, Long> {

    @Query(
            "SELECT p.current FROM PointEntity p "
                    + "WHERE p.member.id = :memberId "
                    + "ORDER BY p.id DESC LIMIT 1")
    Optional<Integer> findCurrentPointByMemberId(Long memberId);
}
