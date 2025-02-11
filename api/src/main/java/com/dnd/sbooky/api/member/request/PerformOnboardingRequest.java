package com.dnd.sbooky.api.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PerformOnboardingRequest(@NotBlank @Size(min = 1, max = 10) String nickname) {}
