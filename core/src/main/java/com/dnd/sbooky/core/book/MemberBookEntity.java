package com.dnd.sbooky.core.book;

import com.dnd.sbooky.core.BaseEntity;
import com.dnd.sbooky.core.member.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "member_book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberBookEntity extends BaseEntity {

    private static final String ENTITY_PREFIX = "member_book";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_read_status", nullable = false)
    private ReadStatus readStatus;

    @Column(name = ENTITY_PREFIX + "_completed_at")
    private LocalDate completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity bookEntity;

    @Builder
    private MemberBookEntity(
            ReadStatus readStatus, MemberEntity memberEntity, BookEntity bookEntity) {
        this.readStatus = readStatus;
        this.memberEntity = memberEntity;
        this.bookEntity = bookEntity;
    }

    public static MemberBookEntity newInstance(
            MemberEntity memberEntity, BookEntity bookEntity, ReadStatus readStatus) {
        if (memberEntity == null || bookEntity == null) {
            throw new IllegalArgumentException(); // todo 추후 커스텀한 예외로 수정
        }
        return MemberBookEntity.builder()
                .readStatus(readStatus)
                .memberEntity(memberEntity)
                .bookEntity(bookEntity)
                .build();
    }

    public void updateStatus(ReadStatus readStatus) {
        this.readStatus = readStatus;
    }

    public boolean isSameMember(Long memberId) {
        return memberEntity.getId().equals(memberId);
    }

    public boolean isHidden() {
        return !memberEntity.isBookPublic();
    }
}
