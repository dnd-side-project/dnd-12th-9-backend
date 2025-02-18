package com.dnd.sbooky.core.item;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ItemRandomFetcher {
    private final List<Long> itemIds;

    public ItemRandomFetcher(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    public Long generateRandomItem() {
        return ThreadLocalRandom.current().nextLong(1, itemIds.size() + 1);
    }
}
