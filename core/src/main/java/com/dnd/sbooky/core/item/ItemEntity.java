package com.dnd.sbooky.core.item;

import com.dnd.sbooky.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemEntity extends BaseEntity {

    private static final String ENTITY_PREFIX = "item";

    @Id
    @Column(name = ENTITY_PREFIX + "_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_type", nullable = false)
    private ItemType type;

    @Column(name = ENTITY_PREFIX + "_name", nullable = false)
    private String name;

    @Builder
    private ItemEntity(Long id, ItemType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public static ItemEntity newInstance(Long id, ItemType type, String name) {
        return ItemEntity.builder().id(id).type(type).name(name).build();
    }
}
