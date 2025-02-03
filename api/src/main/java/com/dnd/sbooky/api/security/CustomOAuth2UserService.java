package com.dnd.sbooky.api.security;

import com.dnd.sbooky.core.like.LikeEntity;
import com.dnd.sbooky.core.like.LikeRepository;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
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
    private final LikeRepository likeRepository;

    /**
     * kakaoId와 registrationId로 회원을 찾거나 저장한다.
     *
     * @param userRequest the user request
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName =
                userRequest
                        .getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName();
        OAuth2UserDTO oAuth2UserDTO =
                OAuth2UserDTO.of(oAuth2UserAttributes, registrationId, userNameAttributeName);
        MemberEntity member = getOrSave(oAuth2UserDTO);
        return new PrincipalDetails(member, oAuth2UserAttributes, userNameAttributeName);
    }

    private MemberEntity getOrSave(OAuth2UserDTO oAuth2UserDTO) {
        MemberEntity member =
                memberRepository
                        .findByKakaoIdAndRegistrationId(
                                oAuth2UserDTO.kakaoId(), oAuth2UserDTO.registrationId())
                        .orElseGet(oAuth2UserDTO::toEntity);
        MemberEntity memberEntity = memberRepository.save(member);
        likeRepository.save(LikeEntity.newInstance(memberEntity.getId()));
        return memberEntity;
    }
}
