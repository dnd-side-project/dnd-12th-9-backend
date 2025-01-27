package com.dnd.book.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByKakaoIdAndRegistrationId(String kakaoid, String registrationId);
}
