package com.dnd.sbooky.core.book;

import com.dnd.sbooky.core.book.dto.MemberBookResponseDTO;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface MemberBookRepositoryCustom {

    List<MemberBookResponseDTO> findMemberBookByMemberIdAndReadStatus(Long memberId, ReadStatus readStatus);

}
