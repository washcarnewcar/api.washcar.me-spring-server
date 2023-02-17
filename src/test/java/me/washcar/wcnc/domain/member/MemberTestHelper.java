package me.washcar.wcnc.domain.member;

import static me.washcar.wcnc.domain.member.MemberRole.*;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MemberTestHelper {

	private String generateRandomName() {
		return UUID.randomUUID().toString().substring(0, 3);
	}

	private String generateRandomPassword() {
		return UUID.randomUUID().toString().substring(0, 7);
	}

	private String generateRandomTelephone() {
		StringBuilder stringBuilder = new StringBuilder();
		Random random = new Random();
		stringBuilder.append("010");
		for (int i = 0; i < 8; i++) {
			stringBuilder.append(random.nextInt());
		}
		return stringBuilder.toString();
	}

	public Member makeStaticMember() {
		return Member.builder()
			.loginId("00AA")
			.nickname("StaticMember-00AA")
			.memberRole(ROLE_USER)
			.memberStatus(MemberStatus.ACTIVE)
			.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
			.loginPassword("StaticMember-PASSWORD")
			.telephone("01010002000")
			.build();
	}

	public Member makeRandomMember() {
		String prefix = "RandomMember-";
		String randomSuffix = generateRandomName();
		String randomName = prefix.concat(randomSuffix);
		String randomPassword = prefix.concat(generateRandomPassword());
		String randomTelephone = generateRandomTelephone();
		return Member.builder()
			.loginId(randomSuffix)
			.nickname(randomName)
			.memberRole(ROLE_USER)
			.memberStatus(MemberStatus.ACTIVE)
			.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
			.loginPassword(randomPassword)
			.telephone(randomTelephone)
			.build();
	}

	public Member makeActiveMember() {
		return makeRandomMember();
	}

	public Member makeInactiveMember() {
		String prefix = "RandomMember-";
		String randomSuffix = generateRandomName();
		String randomName = prefix.concat(randomSuffix);
		String randomPassword = prefix.concat(generateRandomPassword());
		String randomTelephone = generateRandomTelephone();
		return Member.builder()
			.loginId(randomSuffix)
			.nickname(randomName)
			.memberRole(ROLE_USER)
			.memberStatus(MemberStatus.INACTIVE)
			.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
			.loginPassword(randomPassword)
			.telephone(randomTelephone)
			.build();
	}
}
