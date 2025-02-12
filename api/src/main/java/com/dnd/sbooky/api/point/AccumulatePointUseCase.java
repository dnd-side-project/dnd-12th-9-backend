package com.dnd.sbooky.api.point;

import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.point.PointEntity;
import com.dnd.sbooky.core.point.PointPolicy;
import com.dnd.sbooky.core.point.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccumulatePointUseCase {

    private final PointRepository pointRepository;

    public void accumulate(MemberEntity member, PointPolicy pointPolicy) {
        int currentPoint = getCurrentPointByMemberId(member);

        PointEntity pointEntity = PointEntity.newInstance(member, currentPoint, pointPolicy);
        pointRepository.save(pointEntity);
    }

    /**
     * 회원 ID로 현재 포인트를 조회한다.
     *
     * @param member 회원 엔티티
     * @return 회원의 현재 포인트
     */
    private int getCurrentPointByMemberId(MemberEntity member) {
        return pointRepository.findCurrentPointByMemberId(member.getId());
    }
}
