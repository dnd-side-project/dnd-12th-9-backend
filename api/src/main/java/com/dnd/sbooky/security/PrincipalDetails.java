package com.dnd.sbooky.security;

import com.dnd.sbooky.member.MemberEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record PrincipalDetails(
        MemberEntity member,
        Map<String, Object> attributes,
        String attributeKey
) implements OAuth2User, UserDetails{
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getId().toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(member.getRole().name()));
    }

    @Override
    public String getName() {
        return attributes.get(attributeKey).toString(); // kakaoId
    }
}
