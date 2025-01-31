package com.dnd.sbooky.core.book;

import com.dnd.sbooky.core.book.dto.MemberBookResponseDTO;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MemberBookRepositoryCustom {

    List<MemberBookResponseDTO> findMemberBookByMemberIdAndReadStatus(
            Long memberId, ReadStatus readStatus);
}
