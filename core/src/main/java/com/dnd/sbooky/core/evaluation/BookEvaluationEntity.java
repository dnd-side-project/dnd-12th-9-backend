package com.dnd.sbooky.core.evaluation;

import com.dnd.sbooky.core.book.MemberBookEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "book_evaluation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_book_id")
    private MemberBookEntity memberBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id")
    private EvaluationEntity evaluation;

    private BookEvaluationEntity(MemberBookEntity memberBookEntity, EvaluationEntity evaluation) {
        this.memberBook = memberBookEntity;
        this.evaluation = evaluation;
    }

    public static BookEvaluationEntity newInstance(
            MemberBookEntity memberBookEntity, EvaluationEntity evaluation) {
        return new BookEvaluationEntity(memberBookEntity, evaluation);
    }
}
