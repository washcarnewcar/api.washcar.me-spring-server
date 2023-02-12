package me.washcar.wcnc.domain.auth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.auth.KakaoMember;
import me.washcar.wcnc.domain.auth.OAuth2Member;
import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.domain.member.OAuth;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.dao.OAuthRepository;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {

	private final OAuthRepository oAuthRepository;
	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		String provider = userRequest.getClientRegistration().getRegistrationId();    // google, kakao, ...

		OAuth2Member oAuth2Member = switch (provider) {
			case "kakao" -> new KakaoMember(oAuth2User.getAttributes());
			case "google", "naver" ->
				throw new OAuth2AuthorizationException(new OAuth2Error("Not Implemented Provider"));
			default -> throw new OAuth2AuthorizationException(new OAuth2Error("Not Supported Provider"));
		};

		OAuth oAuth = oAuthRepository.findByProviderAndProviderId(oAuth2Member.getProvider(),
			oAuth2Member.getProviderId()).orElse(null);

		// 회원가입이 되어있지 않을 때
		if (oAuth == null) {
			// 회원가입을 시킨다.
			// 이 과정에서 member와 oAuth에 데이터가 들어감
			Member newMember = oAuth2Member.getMember();
			memberRepository.save(newMember);
			OAuth newOAuth = oAuth2Member.getOAuth();
			oAuthRepository.save(newOAuth);
		}
		// 회원가입이 되어있으면, 회원 정보를 OAuth2Member에게 넣어준다.
		else {
			oAuth2Member.setOAuth(oAuth);
		}

		// 회원가입이 되어있으면
		// Authorization을 담은 상태의 OAuth2Member를 반환한다.
		return oAuth2Member;
	}
}
