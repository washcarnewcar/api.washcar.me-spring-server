package me.washcar.wcnc.domain.member;

import static me.washcar.wcnc.domain.member.MemberRole.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

import me.washcar.wcnc.domain.member.entity.Member;

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
			.name("StaticMember-00AA")
			.memberRole(USER)
			.memberStatus(MemberStatus.ACTIVE)
			.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
			.password("StaticMember-PASSWORD")
			.telephone("01010002000")
			.stores(new ArrayList<>())
			.reservations(new ArrayList<>())
			.build();
	}

	public Member makeRandomMember() {
		String prefix = "RandomMember-";
		String randomName = prefix.concat(generateRandomName());
		String randomPassword = prefix.concat(generateRandomPassword());
		String randomTelephone = generateRandomTelephone();
		return Member.builder()
			.name(randomName)
			.memberRole(USER)
			.memberStatus(MemberStatus.ACTIVE)
			.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
			.password(randomPassword)
			.telephone(randomTelephone)
			.stores(new ArrayList<>())
			.reservations(new ArrayList<>())
			.build();
	}

	public Member makeActiveMember() {
		return makeRandomMember();
	}

	public Member makeInactiveMember() {
		String prefix = "RandomMember-";
		String randomName = prefix.concat(generateRandomName());
		String randomPassword = prefix.concat(generateRandomPassword());
		String randomTelephone = generateRandomTelephone();
		return Member.builder()
			.name(randomName)
			.memberRole(USER)
			.memberStatus(MemberStatus.INACTIVE)
			.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
			.password(randomPassword)
			.telephone(randomTelephone)
			.stores(new ArrayList<>())
			.reservations(new ArrayList<>())
			.build();
	}
}
