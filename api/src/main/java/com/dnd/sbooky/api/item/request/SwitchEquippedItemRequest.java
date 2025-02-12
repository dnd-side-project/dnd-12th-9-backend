package com.dnd.sbooky.api.item.request;

import jakarta.validation.constraints.NotNull;

public record SwitchEquippedItemRequest(
        @NotNull Long equippedItemId,
        @NotNull Long toEquipItemId) {}
