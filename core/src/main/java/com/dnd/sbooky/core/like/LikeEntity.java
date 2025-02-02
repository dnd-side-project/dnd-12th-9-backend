package com.dnd.sbooky.core.like;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeEntity {
    private static final String ENTITY_PREFIX = "likes";
    public static final long START_COUNT = 0L;

    @Id
    @Column(name = ENTITY_PREFIX + "_id")
    private Long id;

    @Column(name = ENTITY_PREFIX + "_count")
    private Long count;

    public static LikeEntity newInstance(Long likeId) {
        return new LikeEntity(likeId, START_COUNT);
    }

    public Long add(long count) {
        this.count += count;
        return this.count;
    }
}
