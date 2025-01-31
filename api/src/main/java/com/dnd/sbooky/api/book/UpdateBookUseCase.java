package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.request.UpdateBookRequest;
import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.BookEntity;
import com.dnd.sbooky.core.book.MemberBookEntity;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UpdateBookUseCase {

    private final MemberBookRepository memberBookRepository;

    public void update(Long memberId, Long memberBookId, UpdateBookRequest request) {

        MemberBookEntity memberBookEntity = memberBookRepository.findById(memberBookId)
                .orElseThrow(() -> new ApiException(ErrorType.NOT_FOUND));

        validateMemberAccess(memberId, memberBookEntity);

        memberBookEntity.updateStatus(ReadStatus.valueOf(request.readStatus()));

        BookEntity bookEntity = memberBookEntity.getBookEntity();
        bookEntity.update(request.author(), request.title(), request.publishedAt());
    }

    private void validateMemberAccess(Long memberId, MemberBookEntity memberBook) {
        if (!memberBook.getMemberEntity().getId().equals(memberId)) {
            throw new ApiException(ErrorType.FORBIDDEN);
        }
    }
}
