package com.dnd.sbooky.core.book;

import com.dnd.sbooky.core.book.dto.FindBookDetailsDTO;
import com.dnd.sbooky.core.book.dto.FindBooksDTO;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MemberBookRepositoryCustom {

    FindBooksDTO findMemberBookByMemberIdAndReadStatus(Long memberId, ReadStatus readStatus);

    FindBookDetailsDTO findBookDetails(Long memberBookId);

    boolean checkBookExist(Long memberId, String title, String author);

    long findCompletedBooks(Long memberId);
}
