package me.washcar.wcnc.domain.auth.service;

import static me.washcar.wcnc.domain.member.MemberRole.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.auth.SignupToken;
import me.washcar.wcnc.domain.auth.dao.SignupTokenRepository;
import me.washcar.wcnc.domain.auth.dto.request.CheckMemberIdDto;
import me.washcar.wcnc.domain.auth.dto.request.CheckTelDto;
import me.washcar.wcnc.domain.auth.dto.request.LoginDto;
import me.washcar.wcnc.domain.auth.dto.request.SignupDto;
import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.domain.member.MemberAuthenticationType;
import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
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

	/**
	 *	userId와 password를 입력받아 인증 후 해당 id의 Member객체를 반환한다.
	 */
	public Member authenticate(LoginDto loginDto) throws AuthenticationException {
		String userId = loginDto.getMemberId();
		String password = loginDto.getPassword();
		// userId와 password로 인증을 수행한다. => 성공하면 계속 진행, 실패하면 403 응답
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(userId, password));
		return (Member)authentication.getPrincipal();
	}

	public void checkMemberId(CheckMemberIdDto checkMemberIdDto) {
		String memberId = checkMemberIdDto.getMemberId();
		Optional<Member> member = memberRepository.findByMemberId(memberId);

		if (member.isPresent()) {
			throw new BusinessException(BusinessError.MEMBER_ID_DUPLICATED);
		}

	}

	public void checkTel(CheckTelDto checkTelDto) {
		// 동일한 휴대폰 번호로 가입한 이력이 있는지 확인
		Optional<Member> member = memberRepository.findByTelephone(checkTelDto.getTelephone());
		if (member.isPresent()) {
			throw new BusinessException(BusinessError.MEMBER_TEL_DUPLICATED);
		}

		SignupToken foundFirstSignupToken = signupTokenRepository.findFirstByTelephoneOrderByCreatedDateDesc(
			checkTelDto.getTelephone()).orElse(null);
		// 이미 인증번호를 보낸 이력이 있을 때
		if (foundFirstSignupToken != null) {
			// 30초가 지나지 않았다면 예외를 발생시킨다.
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime plus30SecondsAtCreatedDate = foundFirstSignupToken.getCreatedDate().plusSeconds(30);
			if (now.isBefore(plus30SecondsAtCreatedDate)) {
				throw new BusinessException(BusinessError.TOO_FAST_TOKEN);
			}
		}

		Random random = new Random();
		int randomInt = random.nextInt(1000000);
		String token = String.format("%06d", randomInt);

		// TODO 메시지로 토큰 보내기

		SignupToken signupToken = SignupToken.builder()
			.telephone(checkTelDto.getTelephone())
			.token(token)
			.build();
		signupTokenRepository.save(signupToken);
	}

	public boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals(MemberRole.ROLE_SUPERMAN.getName()) ||
				authority.getAuthority().equals(MemberRole.ROLE_ADMIN.getName())) {
				return true;
			}
		}
		return false;
	}

	public void signup(SignupDto signupDto) {

		// 인증번호 유효한지 확인
		// 한시간 안에 전송했던 인증번호들과만 비교
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneHourBefore = now.minusHours(1);
		boolean isSignupToken = signupTokenRepository.existsByTelephoneAndTokenAndCreatedDateAfter(
			signupDto.getTelephone(), signupDto.getToken(), oneHourBefore);
		if (!isSignupToken) {
			throw new BusinessException(BusinessError.TOKEN_NOT_VALID);
		}

		// id가 중복된 Member가 있는지
		boolean isMemberIdDuplicated = memberRepository.existsByMemberId(signupDto.getMemberId());
		if (isMemberIdDuplicated) {
			throw new BusinessException(BusinessError.MEMBER_ID_DUPLICATED);
		}

		// telephone이 중복된 Member가 있는지
		boolean isTelephoneDuplicated = memberRepository.existsByTelephone(signupDto.getTelephone());
		if (isTelephoneDuplicated) {
			throw new BusinessException(BusinessError.MEMBER_TEL_DUPLICATED);
		}

		// member 객체 생성
		Member member = Member.builder()
			.memberId(signupDto.getMemberId())
			.name(signupDto.getName())
			.password(passwordEncoder.encode(signupDto.getPassword()))
			.telephone(signupDto.getTelephone())
			.memberRole(ROLE_USER)
			.memberStatus(MemberStatus.ACTIVE)
			.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
			.build();

		memberRepository.save(member);

		// 해당 member와 관련된 인증번호들은 데이터베이스에서 삭제
		long deleteCount = signupTokenRepository.deleteAllByTelephone(signupDto.getTelephone());
		if (deleteCount <= 0) {
			throw new BusinessException(BusinessError.TOKEN_NOT_DELETED);
		}
	}
}
