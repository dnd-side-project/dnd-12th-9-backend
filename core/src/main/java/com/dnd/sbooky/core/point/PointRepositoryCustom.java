package com.dnd.sbooky.core.point;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PointRepositoryCustom {

    int findCurrentPointByMemberId(Long memberId);
}
