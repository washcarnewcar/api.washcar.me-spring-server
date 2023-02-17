package me.washcar.wcnc.domain.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.washcar.wcnc.domain.member.MemberAuthenticationType;
import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.entity.Member;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

	private String loginId;

	private String uuid;

	private MemberStatus memberStatus;

	private MemberRole memberRole;

	private MemberAuthenticationType memberAuthenticationType;

	private String nickname;

	private String telephone;

	public MemberDto(Member member) {
		this.loginId = member.getLoginId();
		this.uuid = member.getUuid();
		this.memberStatus = member.getMemberStatus();
		this.memberRole = member.getMemberRole();
		this.memberAuthenticationType = member.getMemberAuthenticationType();
		this.nickname = member.getNickname();
		this.telephone = member.getTelephone();
	}

}
