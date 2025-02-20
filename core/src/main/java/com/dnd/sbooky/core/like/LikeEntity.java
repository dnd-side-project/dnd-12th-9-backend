package com.dnd.sbooky.core.like;

import com.dnd.sbooky.core.member.MemberEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeEntity {
    private static final String ENTITY_PREFIX = "likes";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    private LikeEntity(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    public static LikeEntity newInstance(MemberEntity memberEntity) {
        return new LikeEntity(memberEntity);
    }
}
