package com.dnd.sbooky.core.item;

import com.dnd.sbooky.core.item.dto.FindItemDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MemberItemRepositoryCustom {
    List<FindItemDTO> findItemsByMemberId(Long memberId);

    List<FindItemDTO> findEquippedItemsByMemberId(Long memberId);

    Optional<MemberItemEntity> findMemberItemByMemberIdAndItemId(Long memberId, Long itemId);

    boolean existsByMemberIdAndItemId(Long memberId, Long itemId);
}
