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
import lombok.Builder;
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

    @Column(name = ENTITY_PREFIX + "_name", nullable = false)
    private String name;

    @Column(name = ENTITY_PREFIX + "_code", nullable = false)
    private String code;

    @Builder
    private ItemEntity(ItemType type, String name, String code) {
        this.type = type;
        this.name = name;
        this.code = code;
    }

    public static ItemEntity newInstance(ItemType type, String name, String code) {
        return ItemEntity.builder()
                .type(type)
                .name(name)
                .code(code)
                .build();
    }
}
