package com.dnd.sbooky.core;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @CreationTimestamp @Column private LocalDateTime createdAt;

    @UpdateTimestamp @Column private LocalDateTime updatedAt;
}
