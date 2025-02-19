package com.dnd.sbooky.api.point;

import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.point.response.GetPointResponse;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.member.MemberRepository;
import com.dnd.sbooky.core.point.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetPointUseCase {

    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public GetPointResponse get(Long memberId) {

        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND);
        }

        return GetPointResponse.from(pointRepository.findCurrentPointByMemberId(memberId));
    }
}
