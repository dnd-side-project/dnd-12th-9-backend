package com.dnd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExampleEntity extends BaseEntity {

    @Column
    private String name;

    public ExampleEntity(String name) {
        this.name = name;
    }
}
