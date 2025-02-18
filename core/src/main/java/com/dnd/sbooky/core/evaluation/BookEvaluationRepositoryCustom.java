package com.dnd.sbooky.core.evaluation;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BookEvaluationRepositoryCustom {

    boolean existsByMemberBookId(Long memberBookId);
}
