package com.dnd.sbooky.core.point;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointEntity, Long>, PointRepositoryCustom {}
