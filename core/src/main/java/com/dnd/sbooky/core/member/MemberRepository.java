package com.dnd.sbooky.core.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByKakaoIdAndRegistrationId(String kakaoId, String registrationId);
}
