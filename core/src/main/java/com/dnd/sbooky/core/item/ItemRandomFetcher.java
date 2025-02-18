package com.dnd.sbooky.core.item;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class ItemRandomFetcher {
    private final long ITEM_SIZE;

    public ItemRandomFetcher() {
        this.ITEM_SIZE = ItemCode.values().length;
    }

    public Long generateRandomItem() {
        return ThreadLocalRandom.current().nextLong(1, ITEM_SIZE + 1);
    }
}
