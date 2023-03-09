package me.washcar.wcnc.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.entity.Member;

@Getter
@NoArgsConstructor
public class MemberMeDto {

	private String nickname;

	private MemberRole memberRole;

	@Builder
	@SuppressWarnings("unused")
	private MemberMeDto(String nickname, MemberRole memberRole) {
		this.nickname = nickname;
		this.memberRole = memberRole;
	}

	public static MemberMeDto from(Member member) {
		return MemberMeDto.builder()
			.nickname(member.getNickname())
			.memberRole(member.getMemberRole())
			.build();
	}

}
