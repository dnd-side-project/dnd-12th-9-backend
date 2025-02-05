package com.dnd.sbooky.core.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberItemRepository
        extends JpaRepository<MemberItemEntity, Long>{}
