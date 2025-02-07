package com.dnd.sbooky.api.evaluation;

import com.dnd.sbooky.api.book.exception.BookNotFoundException;
import com.dnd.sbooky.api.evaluation.request.RegisterEvaluationRequest;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.MemberBookEntity;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.evaluation.BookEvaluationEntity;
import com.dnd.sbooky.core.evaluation.BookEvaluationRepository;
import com.dnd.sbooky.core.evaluation.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterEvaluationUseCase {

    private final MemberBookRepository memberBookRepository;
    private final BookEvaluationRepository bookEvaluationRepository;
    private final EvaluationRepository evaluationRepository;

    public void register(Long memberBookId, Long memberId, RegisterEvaluationRequest request) {

        MemberBookEntity memberBook =
                memberBookRepository
                        .findById(memberBookId)
                        .orElseThrow(() -> new BookNotFoundException(ErrorType.BOOK_NOT_FOUND));

        request.keywordIds().forEach(keywordId -> {
            EvaluationevaluationRepository.findById(keywordId)
                    .orElseThrow(() -> new EvaluationKeywordNotFoundException(ErrorType.EVALUATION_KEYWORD_NOT_FOUND));
            bookEvaluationRepository.save(BookEvaluationEntity.newInstance(memberBook, ))
        });

    }
}
