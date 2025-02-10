package com.dnd.sbooky.api.member.response;

public record GetMemberResponse(String memberId) {
    public static GetMemberResponse of(String memberId) {
        return new GetMemberResponse(memberId);
    }
}
