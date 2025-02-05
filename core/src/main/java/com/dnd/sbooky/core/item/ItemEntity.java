package com.dnd.sbooky.core.item;

import com.dnd.sbooky.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemEntity extends BaseEntity {

    private static final String ENTITY_PREFIX = "item";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_type", nullable = false)
    private ItemType type;

    private ItemEntity(ItemType type) {
        this.type = type;
    }

    public static ItemEntity newInstance(ItemType type) {
        return new ItemEntity(type);
    }
}
