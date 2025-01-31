package com.dnd.sbooky.api.security;

import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.Role;
import java.util.Map;

public record OAuth2UserDTO(String kakaoId, String registrationId) {

    public static OAuth2UserDTO of(
            Map<String, Object> oAuth2UserAttributes,
            String registrationId,
            String userNameAttributeName) {
        return new OAuth2UserDTO(
                oAuth2UserAttributes.get(userNameAttributeName).toString(), registrationId);
    }

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .kakaoId(kakaoId)
                .registrationId(registrationId)
                .role(Role.ROLE_MEMBER)
                .build();
    }
}
