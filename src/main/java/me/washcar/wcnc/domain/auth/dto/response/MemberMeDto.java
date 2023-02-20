package me.washcar.wcnc.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.washcar.wcnc.domain.member.entity.Member;

@Getter
@NoArgsConstructor
public class MemberMeDto {
	private String nickname;

	@Builder
	@SuppressWarnings("unused")
	private MemberMeDto(String nickname) {
		this.nickname = nickname;
	}

	public MemberMeDto(Member member) {
		this.nickname = member.getNickname();
	}

}
