package com.dnd.sbooky.api.security;

import static com.dnd.sbooky.api.support.error.ErrorType.ITEM_NOT_FOUND;

import com.dnd.sbooky.api.item.exception.ItemNotFoundException;
import com.dnd.sbooky.core.item.ItemEntity;
import com.dnd.sbooky.core.item.ItemRepository;
import com.dnd.sbooky.core.item.MemberItemEntity;
import com.dnd.sbooky.core.item.MemberItemRepository;
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
    private final MemberItemRepository memberItemRepository;
    private final ItemRepository itemRepository;

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

    /**
     * 회원이 있을 경우에는 회원을 반환하고, 없을 경우에는 회원을 저장(기본 아이템 저장, 좋아요 0으로 저장)하고 반환한다.
     * @param oAuth2UserDTO
     * @return
     */
    private MemberEntity getOrSave(OAuth2UserDTO oAuth2UserDTO) {
        return memberRepository
                .findByKakaoIdAndRegistrationId(
                        oAuth2UserDTO.kakaoId(), oAuth2UserDTO.registrationId())
                .orElseGet(
                        () -> {
                            MemberEntity savedMember =
                                    memberRepository.save(oAuth2UserDTO.toEntity());
                            likeRepository.save(LikeEntity.newInstance(savedMember.getId()));
                            ItemEntity itemEntity =
                                    itemRepository
                                            .findById(1L)
                                            .orElseThrow(
                                                    () ->
                                                            new ItemNotFoundException(
                                                                    ITEM_NOT_FOUND));
                            memberItemRepository.save(
                                    MemberItemEntity.newInstance(savedMember, itemEntity));
                            return savedMember;
                        });
    }
}
