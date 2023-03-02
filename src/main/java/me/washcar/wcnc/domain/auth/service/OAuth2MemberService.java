package me.washcar.wcnc.domain.auth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.auth.adapter.KakaoAdapterImpl;
import me.washcar.wcnc.domain.auth.adapter.NaverAdapterImpl;
import me.washcar.wcnc.domain.auth.adapter.OAuth2Adapter;
import me.washcar.wcnc.domain.member.MemberAuthenticationType;
import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.dao.OAuthRepository;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.domain.member.entity.OAuth;
import me.washcar.wcnc.global.error.BusinessError;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {

	private final OAuthRepository oAuthRepository;
	private final MemberRepository memberRepository;

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		String provider = userRequest.getClientRegistration().getRegistrationId();    // google, kakao, ...

		OAuth2Adapter oAuth2Adapter = switch (provider) {
			case "kakao" -> new KakaoAdapterImpl(oAuth2User.getAttributes());
			case "naver" -> new NaverAdapterImpl(oAuth2User.getAttributes());
			default -> throw new OAuth2AuthenticationException(
				new OAuth2Error(BusinessError.OAUTH_NOT_SUPPORTED_PROVIDER.getMessage()));
		};

		// 회원가입이 되어있는지 확인
		OAuth oAuth = oAuthRepository.findByProviderAndProviderId(oAuth2Adapter.getProvider(),
			oAuth2Adapter.getProviderId()).orElse(null);

		// 회원가입이 되어있지 않을 때 회원가입을 한다.
		if (oAuth == null) {
			// 전화번호가 중복되었는지 확인
			if (memberRepository.existsByTelephone(oAuth2Adapter.getTelephone())) {
				throw new OAuth2AuthenticationException(
					new OAuth2Error(BusinessError.MEMBER_TELEPHONE_DUPLICATED.getMessage()));
			}

			Member newMember = Member.builder()
				.memberStatus(MemberStatus.ACTIVE)
				.memberRole(MemberRole.ROLE_USER)
				.memberAuthenticationType(MemberAuthenticationType.OAUTH)
				.nickname(oAuth2Adapter.getNickname())
				.telephone(oAuth2Adapter.getTelephone())
				.build();
			memberRepository.save(newMember);

			OAuth newOAuth = OAuth.builder()
				.member(newMember)
				.provider(oAuth2Adapter.getProvider())
				.providerId(oAuth2Adapter.getProviderId())
				.build();
			oAuthRepository.save(newOAuth);

			oAuth2Adapter.setMemberField(newMember);
		}

		// 회원가입이 되어있으면 기존에 있던 권한과 uuid를 가져온다.
		else {
			oAuth2Adapter.setMemberField(oAuth.getMember());
		}

		// 활성화/비활성화 구현
		if (!oAuth2Adapter.isEnabled()) {
			throw new OAuth2AuthenticationException(new OAuth2Error(BusinessError.MEMBER_DISABLED.getMessage()));
		}

		// 여기서 반환된 oAuth2Adapter는 OAuth2SuccessHandler에서 사용됩니다.
		return oAuth2Adapter;
	}
}
