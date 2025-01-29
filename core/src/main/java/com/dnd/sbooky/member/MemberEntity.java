package com.dnd.sbooky.member;

import com.dnd.sbooky.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberEntity extends BaseEntity {

    private static final String ENTITY_PREFIX = "member";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ENTITY_PREFIX + "_nickname")
    private String nickname;

    @Column(name = ENTITY_PREFIX + "_introduction")
    private String introduction;

    @Column(name = ENTITY_PREFIX + "_kakao_id")
    private String kakaoId;

    @Column(name = ENTITY_PREFIX + "_registration_id")
    private String registrationId;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_role")
    private Role role;
    @Builder
    private MemberEntity(String nickname, String introduction, String kakaoId, String registrationId, Role role) {
        this.kakaoId = kakaoId;
        this.registrationId = registrationId;
        this.nickname = nickname;
        this.introduction = introduction;
        this.role = role;
    }

    public static MemberEntity newInstance(String nickname, String introduction) {
        return MemberEntity.builder()
                .nickname(nickname)
                .introduction(introduction)
                .build();
    }
}
