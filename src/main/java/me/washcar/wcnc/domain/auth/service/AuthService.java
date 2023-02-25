package me.washcar.wcnc.domain.auth.service;

import static me.washcar.wcnc.domain.member.MemberRole.*;

import java.time.LocalDateTime;
import java.util.Random;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.aligo.service.AligoService;
import me.washcar.wcnc.domain.auth.adapter.AuthenticationAdapter;
import me.washcar.wcnc.domain.auth.dao.SignupPinNumberRepository;
import me.washcar.wcnc.domain.auth.dto.request.LoginDto;
import me.washcar.wcnc.domain.auth.dto.request.SignupDto;
import me.washcar.wcnc.domain.auth.dto.response.MemberMeDto;
import me.washcar.wcnc.domain.auth.entity.SignupPinNumber;
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
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final MemberRepository memberRepository;
	private final SignupPinNumberRepository signupPinNumberRepository;
	private final PasswordEncoder passwordEncoder;
	private final CookieService cookieService;
	private final AligoService aligoService;

	/**
	 *	loginId 와 loginPassword 를 입력받아 인증 후 해당 id의 MemberMeDto 객체를 반환한다.
	 */
	public MemberMeDto login(LoginDto loginDto, HttpServletResponse response) throws AuthenticationException {
		String loginId = loginDto.getLoginId();
		String loginPassword = loginDto.getLoginPassword();

		// loginId 와 loginPassword 로 인증을 수행한다. => 성공하면 계속 진행, 실패하면 403 응답
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginId, loginPassword));

		AuthenticationAdapter adapter = (AuthenticationAdapter)authentication.getPrincipal();

		cookieService.authenticate(adapter.getUuid(), adapter.getMemberRole(), response);

		return this.getMemberMeByUuid(adapter.getUuid());
	}

	@Transactional(readOnly = true)
	public void checkLoginId(String loginId) {
		if (memberRepository.existsByLoginId(loginId)) {
			throw new BusinessException(BusinessError.LOGIN_ID_DUPLICATED);
		}
	}

	@Transactional
	public void checkTelephone(String telephone) {
		// 동일한 휴대폰 번호로 가입한 이력이 있는지 확인
		if (memberRepository.existsByTelephone(telephone)) {
			throw new BusinessException(BusinessError.MEMBER_TELEPHONE_DUPLICATED);
		}

		SignupPinNumber foundFirstSignupPinNumber = signupPinNumberRepository.findFirstByTelephoneOrderByCreatedDateDesc(
				telephone)
			.orElse(null);
		// 이미 인증번호를 보낸 이력이 있을 때
		if (foundFirstSignupPinNumber != null) {
			// 30초가 지나지 않았다면 예외를 발생시킨다.
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime plus30SecondsAtCreatedDate = foundFirstSignupPinNumber.getCreatedDate().plusSeconds(30);
			if (now.isBefore(plus30SecondsAtCreatedDate)) {
				throw new BusinessException(BusinessError.SIGNUP_PIN_NUMBER_TOO_FAST);
			}
		}

		Random random = new Random();
		int randomInt = random.nextInt(1000000);
		String pinNumber = String.format("%06d", randomInt);

		SignupPinNumber signupPinNumber = SignupPinNumber.builder()
			.telephone(telephone)
			.pinNumber(pinNumber)
			.build();
		signupPinNumberRepository.save(signupPinNumber);

		String message = MessageFormatter.format("세차새차 인증번호 [{}]", pinNumber).getMessage();
		aligoService.sendMessageSingle(message, telephone);
	}

	@Transactional
	public void signup(SignupDto signupDto) {

		// 인증번호 유효한지 확인
		// 한시간 안에 전송했던 인증번호들과만 비교
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneHourBefore = now.minusHours(1);
		if (!signupPinNumberRepository.existsByTelephoneAndPinNumberAndCreatedDateAfter(signupDto.getTelephone(),
			signupDto.getPinNumber(), oneHourBefore)) {
			throw new BusinessException(BusinessError.SIGNUP_PIN_NUMBER_NOT_VALID);
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
		long deleteCount = signupPinNumberRepository.deleteAllByTelephone(signupDto.getTelephone());
		if (deleteCount <= 0) {
			throw new ApplicationException(ApplicationError.PIN_NUMBER_NOT_DELETED);
		}
	}

	@Transactional(readOnly = true)
	public MemberMeDto getMemberMeByUuid(String uuid) {
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
		return new MemberMeDto(member);
	}
}
