package com.dnd.sbooky.api.evaluation;

import com.dnd.sbooky.api.book.v1.exception.BookForbiddenException;
import com.dnd.sbooky.api.book.v1.exception.BookNotFoundException;
import com.dnd.sbooky.api.book.v1.exception.BookReadStatusException;
import com.dnd.sbooky.api.evaluation.exception.EvaluationNotFoundException;
import com.dnd.sbooky.api.evaluation.request.RegisterEvaluationRequest;
import com.dnd.sbooky.api.point.AccumulatePointUseCase;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.MemberBookEntity;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
import com.dnd.sbooky.core.evaluation.BookEvaluationEntity;
import com.dnd.sbooky.core.evaluation.BookEvaluationRepository;
import com.dnd.sbooky.core.evaluation.EvaluationEntity;
import com.dnd.sbooky.core.evaluation.EvaluationRepository;
import com.dnd.sbooky.core.point.PointPolicy;
import java.util.List;
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
    private final AccumulatePointUseCase accumulatePointUseCase;

    public void register(Long memberBookId, Long memberId, RegisterEvaluationRequest request) {

        MemberBookEntity memberBook = validateAndGetMemberBook(memberBookId, memberId);

        boolean isEvaluated = bookEvaluationRepository.existsByMemberBookId(memberBookId);
        bookEvaluationRepository.deleteAllByMemberBookId(memberBookId);

        List<EvaluationEntity> evaluations = getValidatedEvaluations(request.keywordIds());

        List<BookEvaluationEntity> bookEvaluations =
                evaluations.stream()
                        .map(evaluation -> BookEvaluationEntity.newInstance(memberBook, evaluation))
                        .toList();

        bookEvaluationRepository.saveAll(bookEvaluations);

        if (!isEvaluated) {
            accumulatePointUseCase.accumulate(
                    memberBook.getMemberEntity(), PointPolicy.COMPLETE_BOOK_EVALUATION);
        }
    }

    private MemberBookEntity validateAndGetMemberBook(Long memberBookId, Long memberId) {
        MemberBookEntity memberBook =
                memberBookRepository
                        .findById(memberBookId)
                        .orElseThrow(() -> new BookNotFoundException(ErrorType.BOOK_NOT_FOUND));

        if (!memberBook.isSameMember(memberId)) {
            throw new BookForbiddenException(ErrorType.BOOK_ACCESS_FORBIDDEN);
        }

        if (memberBook.getReadStatus() != ReadStatus.COMPLETED) {
            throw new BookReadStatusException(ErrorType.BOOK_READ_STATUS_NOT_COMPLETED);
        }

        return memberBook;
    }

    private List<EvaluationEntity> getValidatedEvaluations(List<Long> keywordIds) {
        return keywordIds.stream()
                .map(
                        id ->
                                evaluationRepository
                                        .findById(id)
                                        .orElseThrow(
                                                () ->
                                                        new EvaluationNotFoundException(
                                                                ErrorType.EVALUATION_KEYWORD_NOT_FOUND)))
                .toList();
    }
}
