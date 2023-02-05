package me.washcar.wcnc.domain.member.dto.response;

import lombok.Getter;
import lombok.Setter;
import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.domain.member.MemberAuthenticationType;
import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.MemberStatus;

@Getter
@Setter
public class MemberDto {

	private String userId;

	private String uuid;

	private MemberStatus memberStatus;

	private MemberRole memberRole;

	private MemberAuthenticationType memberAuthenticationType;

	private String name;

	private String telephone;

	public MemberDto(Member member) {
		this.userId = member.getUserId();
		this.uuid = member.getUuid();
		this.memberStatus = member.getMemberStatus();
		this.memberRole = member.getMemberRole();
		this.memberAuthenticationType = member.getMemberAuthenticationType();
		this.name = member.getName();
		this.telephone = member.getTelephone();
	}

}
