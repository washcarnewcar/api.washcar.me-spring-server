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

	private boolean isRefreshRequired;

	@Builder
	@SuppressWarnings("unused")
	private MemberMeDto(String nickname, MemberRole memberRole, boolean isRefreshRequired) {
		this.nickname = nickname;
		this.memberRole = memberRole;
		this.isRefreshRequired = isRefreshRequired;
	}

	public static MemberMeDto from(Member member, boolean isRefreshRequired) {
		return MemberMeDto.builder()
			.nickname(member.getNickname())
			.memberRole(member.getMemberRole())
			.isRefreshRequired(isRefreshRequired)
			.build();
	}

}
