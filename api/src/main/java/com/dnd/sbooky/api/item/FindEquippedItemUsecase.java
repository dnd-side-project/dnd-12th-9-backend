package com.dnd.sbooky.api.item;

import com.dnd.sbooky.api.item.response.FindEquippedItemsResponse;
import com.dnd.sbooky.api.item.response.FindItemsResponse;
import com.dnd.sbooky.core.item.ItemType;
import com.dnd.sbooky.core.item.MemberItemRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindEquippedItemUsecase {

    private final MemberItemRepository memberItemRepository;

    @Transactional(readOnly = true)
    public FindEquippedItemsResponse findEquippedItems(Long memberId) {
        Map<ItemType, List<String>> equippedItems =
                memberItemRepository.findEquippedItemsByMemberId(memberId).stream()
                        .collect(
                                Collectors.groupingBy(
                                        findItemDTO -> findItemDTO.type(),
                                        Collectors.mapping(
                                                findItemDTO -> findItemDTO.code(),
                                                Collectors.toList())));
        return FindEquippedItemsResponse.from(equippedItems);
    }
}
