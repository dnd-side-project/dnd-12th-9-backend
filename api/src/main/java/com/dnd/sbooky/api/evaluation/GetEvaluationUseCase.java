package com.dnd.sbooky.api.evaluation;

import com.dnd.sbooky.api.book.v1.exception.BookForbiddenException;
import com.dnd.sbooky.api.book.v1.exception.BookNotFoundException;
import com.dnd.sbooky.api.book.v1.exception.BookReadStatusException;
import com.dnd.sbooky.api.evaluation.response.GetEvaluationResponse;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.MemberBookEntity;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
import com.dnd.sbooky.core.evaluation.EvaluationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetEvaluationUseCase {

    private final EvaluationRepository evaluationRepository;
    private final MemberBookRepository memberBookRepository;

    @Transactional(readOnly = true)
    public List<GetEvaluationResponse> get(Long memberId, Long memberBookId) {

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

        return evaluationRepository.findAllWithSelectedByMemberBookId(memberBookId).stream()
                .map(GetEvaluationResponse::of)
                .toList();
    }
}
