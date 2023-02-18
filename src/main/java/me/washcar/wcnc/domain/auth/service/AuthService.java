package me.washcar.wcnc.domain.auth.service;

import static me.washcar.wcnc.domain.member.MemberRole.*;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.auth.SignupToken;
import me.washcar.wcnc.domain.auth.dao.SignupTokenRepository;
import me.washcar.wcnc.domain.auth.dto.request.LoginDto;
import me.washcar.wcnc.domain.auth.dto.request.SignupDto;
import me.washcar.wcnc.domain.member.MemberAuthenticationType;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.global.error.ApplicationError;
import me.washcar.wcnc.global.error.ApplicationException;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final MemberRepository memberRepository;
	private final SignupTokenRepository signupTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final CookieService cookieService;

	/**
	 *	userId와 password를 입력받아 인증 후 해당 id의 Member객체를 반환한다.
	 */
	public Member login(LoginDto loginDto, HttpServletResponse response) throws AuthenticationException {
		String loginId = loginDto.getLoginId();
		String loginPassword = loginDto.getLoginPassword();
		// userId와 password로 인증을 수행한다. => 성공하면 계속 진행, 실패하면 403 응답
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginId, loginPassword));

		Member loginMember = (Member)authentication.getPrincipal();
		cookieService.authenticate(loginMember, response);
		return loginMember;
	}

	public void checkLoginId(String loginId) {
		if (memberRepository.existsByLoginId(loginId)) {
			throw new BusinessException(BusinessError.LOGIN_ID_DUPLICATED);
		}
	}

	public void checkTelephone(String telephone) {
		// 동일한 휴대폰 번호로 가입한 이력이 있는지 확인
		if (memberRepository.existsByTelephone(telephone)) {
			throw new BusinessException(BusinessError.MEMBER_TELEPHONE_DUPLICATED);
		}

		SignupToken foundFirstSignupToken = signupTokenRepository.findFirstByTelephoneOrderByCreatedDateDesc(telephone)
			.orElse(null);
		// 이미 인증번호를 보낸 이력이 있을 때
		if (foundFirstSignupToken != null) {
			// 30초가 지나지 않았다면 예외를 발생시킨다.
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime plus30SecondsAtCreatedDate = foundFirstSignupToken.getCreatedDate().plusSeconds(30);
			if (now.isBefore(plus30SecondsAtCreatedDate)) {
				throw new BusinessException(BusinessError.SIGNUP_TOKEN_TOO_FAST);
			}
		}

		Random random = new Random();
		int randomInt = random.nextInt(1000000);
		String token = String.format("%06d", randomInt);

		// TODO 메시지로 토큰 보내기

		SignupToken signupToken = SignupToken.builder()
			.telephone(telephone)
			.token(token)
			.build();
		signupTokenRepository.save(signupToken);
	}

	public void signup(SignupDto signupDto) {

		// 인증번호 유효한지 확인
		// 한시간 안에 전송했던 인증번호들과만 비교
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneHourBefore = now.minusHours(1);
		if (!signupTokenRepository.existsByTelephoneAndTokenAndCreatedDateAfter(signupDto.getTelephone(),
			signupDto.getToken(), oneHourBefore)) {
			throw new BusinessException(BusinessError.SIGNUP_TOKEN_NOT_VALID);
		}

		if (memberRepository.existsByLoginId(signupDto.getLoginId())) {
			throw new BusinessException(BusinessError.LOGIN_ID_DUPLICATED);
		}

		if (memberRepository.existsByTelephone(signupDto.getTelephone())) {
			throw new BusinessException(BusinessError.MEMBER_TELEPHONE_DUPLICATED);
		}

		Member member = Member.builder()
			.loginId(signupDto.getLoginId())
			.nickname(signupDto.getNickname())
			.loginPassword(passwordEncoder.encode(signupDto.getLoginPassword()))
			.telephone(signupDto.getTelephone())
			.memberRole(ROLE_USER)
			.memberStatus(MemberStatus.ACTIVE)
			.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
			.build();

		memberRepository.save(member);

		// 해당 member와 관련된 인증번호들은 데이터베이스에서 삭제
		long deleteCount = signupTokenRepository.deleteAllByTelephone(signupDto.getTelephone());
		if (deleteCount <= 0) {
			throw new ApplicationException(ApplicationError.TOKEN_NOT_DELETED);
		}
	}
}
