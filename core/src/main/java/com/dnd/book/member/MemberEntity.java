package com.dnd.book.member;

import com.dnd.book.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "member")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberEntity extends BaseEntity {

    public static final String ENTITY_PREFIX = "member";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ENTITY_PREFIX + "_nickname", nullable = false)
    private String nickname;

    @Column(name = ENTITY_PREFIX + "_introduction", nullable = false)
    private String introduction;

    public static MemberEntity newInstance(String nickname, String introduction) {
        return MemberEntity.builder()
                .nickname(nickname)
                .introduction(introduction)
                .build();
    }
}
