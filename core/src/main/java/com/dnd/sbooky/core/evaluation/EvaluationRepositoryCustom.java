package com.dnd.sbooky.core.evaluation;

import com.dnd.sbooky.core.evaluation.dto.GetEvaluationDTO;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EvaluationRepositoryCustom {

    List<GetEvaluationDTO> findAllWithSelectedByMemberBookId(Long memberBookId);
}
