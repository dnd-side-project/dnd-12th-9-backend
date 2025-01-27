package com.dnd.book.security;

import com.dnd.book.member.MemberEntity;
import com.dnd.book.member.MemberRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    /**
     * kakaoId와 registrationId로 회원을 찾거나 저장한다.
     * @param userRequest the user request
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(oAuth2UserAttributes, registrationId, userNameAttributeName);
        MemberEntity member = getOrSave(oAuth2UserInfo);
        return new PrincipalDetails(member, oAuth2UserAttributes, userNameAttributeName);
    }
    private MemberEntity getOrSave(OAuth2UserInfo oAuth2UserInfo) {
        MemberEntity member = memberRepository.findByKakaoIdAndRegistrationId(oAuth2UserInfo.kakaoId(), oAuth2UserInfo.registrationId())
                .orElseGet(oAuth2UserInfo::toEntity);
        return memberRepository.save(member);
    }

}
