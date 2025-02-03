package com.dnd.sbooky.core.book;

import com.dnd.sbooky.core.book.dto.FindBookDetailsDTO;
import com.dnd.sbooky.core.book.dto.FindBookDTO;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MemberBookRepositoryCustom {

    List<FindBookDTO> findMemberBookByMemberIdAndReadStatus(
            Long memberId, ReadStatus readStatus);

    FindBookDetailsDTO findBookDetails(Long memberBookId);
}
