package com.dnd.sbooky.core.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByKakaoIdAndRegistrationId(String kakaoId, String registrationId);
}
