package com.dnd.sbooky.core.item;

import com.dnd.sbooky.core.BaseEntity;
import com.dnd.sbooky.core.member.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberItemEntity extends BaseEntity {
    private static final String ENTITY_PREFIX = "member_item";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    private ItemEntity itemEntity;

    @Column(name = ENTITY_PREFIX + "_equipped", nullable = false)
    private boolean equipped;
}
