package com.dnd.sbooky.api.item;

import com.dnd.sbooky.api.item.response.FindItemResponse;
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
public class FindItemsUseCase {

    private final MemberItemRepository memberItemRepository;

    @Transactional(readOnly = true)
    public FindItemsResponse findMyItems(Long memberId) {
        Map<ItemType, List<FindItemResponse>> items =
                memberItemRepository.findItemsByMemberId(memberId).stream()
                        .collect(
                                Collectors.groupingBy(
                                        findItemDTO -> findItemDTO.type(),
                                        Collectors.mapping(
                                                findItemDTO ->
                                                        FindItemResponse.from(findItemDTO.name(), findItemDTO.code()),
                                                Collectors.toList())));
        return FindItemsResponse.from(items);
    }
}
