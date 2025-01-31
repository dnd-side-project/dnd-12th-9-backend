package com.dnd.sbooky.core.book;

import com.dnd.sbooky.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "book")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEntity extends BaseEntity {

    private static final String ENTITY_PREFIX = "book";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_id", nullable = false)
    private Long id;

    @Column(name = ENTITY_PREFIX + "_author", nullable = false)
    private String author;

    @Column(name = ENTITY_PREFIX + "_title", nullable = false)
    private String title;

    @Column(name = ENTITY_PREFIX + "_published_at", nullable = false)
    private LocalDate publishedAt;

    @Column(name = ENTITY_PREFIX + "_thumbnail_url")
    // todo : nullable = false
    private String thumbnailUrl;

    @Builder
    private BookEntity(String author, String title, LocalDate publishedAt, String thumbnailUrl) {
        this.author = author;
        this.title = title;
        this.publishedAt = publishedAt;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static BookEntity newInstance(String author, String title, LocalDate publishedAt) {
        return BookEntity.builder()
                .author(author)
                .title(title)
                .publishedAt(publishedAt)
                .build();
    }

    public void update(String author, String title, LocalDate localDate) {
        this.author = author;
        this.title = title;
        this.publishedAt = localDate;
    }
}
