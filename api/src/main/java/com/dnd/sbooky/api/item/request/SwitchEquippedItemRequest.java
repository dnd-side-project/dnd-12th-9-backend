package com.dnd.sbooky.api.item.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
@Schema(description = "아이템 착용 요청 DTO")
public record SwitchEquippedItemRequest(
        @Schema(description = "기존 착용하고 있는 아이템") @NotNull Long equippedItemId,
        @Schema(description = "앞으로 착용할 아이템") @NotNull Long toEquipItemId) {}
