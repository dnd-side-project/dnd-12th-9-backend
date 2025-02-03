package com.dnd.sbooky.core.book;

import com.dnd.sbooky.core.book.dto.BookDetailsResponseDTO;
import com.dnd.sbooky.core.book.dto.BookResponseDTO;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MemberBookRepositoryCustom {

    List<BookResponseDTO> findMemberBookByMemberIdAndReadStatus(
            Long memberId, ReadStatus readStatus);

    BookDetailsResponseDTO findBookDetails(Long memberBookId);
}
