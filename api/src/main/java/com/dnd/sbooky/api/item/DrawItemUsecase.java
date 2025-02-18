package com.dnd.sbooky.api.item;

import static com.dnd.sbooky.api.support.error.ErrorType.*;

import com.dnd.sbooky.api.item.exception.ItemNotFoundException;
import com.dnd.sbooky.api.item.response.DrawItemResponse;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.point.AccumulatePointUseCase;
import com.dnd.sbooky.core.item.ItemCode;
import com.dnd.sbooky.core.item.ItemEntity;
import com.dnd.sbooky.core.item.ItemRandomFetcher;
import com.dnd.sbooky.core.item.ItemRepository;
import com.dnd.sbooky.core.item.MemberItemEntity;
import com.dnd.sbooky.core.item.MemberItemRepository;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import com.dnd.sbooky.core.point.PointPolicy;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DrawItemUsecase {

    private final AccumulatePointUseCase accumulatePointUseCase;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final MemberItemRepository memberItemRepository;

    @Transactional
    public DrawItemResponse drawItem(Long memberId) {
        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        accumulatePointUseCase.accumulate(memberEntity, PointPolicy.DRAW_ITEM);
        Long randomItemId = getRandomItemId();
        ItemEntity itemEntity =
                itemRepository
                        .findById(randomItemId)
                        .orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND));

        if (memberItemRepository.existsByMemberIdAndItemId(memberId, randomItemId)) {
            return new DrawItemResponse(
                    itemEntity.getName(), ItemCode.toCode(randomItemId), "이미 가지고 있어요.");
        }

        MemberItemEntity memberItemEntity = MemberItemEntity.obtainItem(memberEntity, itemEntity);
        memberItemRepository.save(memberItemEntity);

        return new DrawItemResponse(itemEntity.getName(), ItemCode.toCode(randomItemId), null);
    }

    private Long getRandomItemId() {
        List<Long> itemIds = itemRepository.findAllItemIds();
        ItemRandomFetcher itemRandomFetcher = new ItemRandomFetcher(itemIds);
        Long randomItemId = itemRandomFetcher.generateRandomItem();
        return randomItemId;
    }
}
