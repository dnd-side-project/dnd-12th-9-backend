package com.dnd.book.book;

import com.dnd.book.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEntity extends BaseEntity {
    public static final String ENTITY_PREFIX = "book";

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
}
