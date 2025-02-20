package com.dnd.sbooky.core.like;

import com.dnd.sbooky.core.member.MemberEntity;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.LongStream;
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

    public static List<LikeEntity> newInstances(MemberEntity memberEntity, long count) {
        return LongStream.range(0, count).mapToObj(i -> new LikeEntity(memberEntity)).toList();
    }
}
