package com.dnd.sbooky.api.member.response;

public record GetMemberResponse(Long memberId, String nickname) {
    public static GetMemberResponse of(Long memberId, String nickname) {
        return new GetMemberResponse(memberId, nickname);
    }
}
