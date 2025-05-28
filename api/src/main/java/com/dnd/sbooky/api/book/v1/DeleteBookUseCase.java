package com.dnd.sbooky.api.book.v1;

import com.dnd.sbooky.api.book.v1.exception.BookForbiddenException;
import com.dnd.sbooky.api.book.v1.exception.BookNotFoundException;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.MemberBookEntity;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.evaluation.BookEvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteBookUseCase {

    private final MemberBookRepository memberBookRepository;
    private final BookEvaluationRepository bookEvaluationRepository;

    public void delete(Long memberId, Long memberBookId) {

        MemberBookEntity memberBook =
                memberBookRepository
                        .findById(memberBookId)
                        .orElseThrow(() -> new BookNotFoundException(ErrorType.BOOK_NOT_FOUND));

        if (!memberBook.isSameMember(memberId)) {
            throw new BookForbiddenException(ErrorType.BOOK_ACCESS_FORBIDDEN);
        }

        if (bookEvaluationRepository.existsByMemberBookId(memberBookId)) {
            bookEvaluationRepository.deleteAllByMemberBookId(memberBookId);
        }

        memberBookRepository.deleteById(memberBookId);
    }
}
