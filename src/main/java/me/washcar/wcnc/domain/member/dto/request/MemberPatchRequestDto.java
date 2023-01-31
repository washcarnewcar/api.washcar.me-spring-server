package me.washcar.wcnc.domain.member.dto.request;

import lombok.Getter;
import me.washcar.wcnc.domain.member.MemberStatus;

@Getter
public class MemberPatchRequestDto {

	private MemberStatus memberStatus;

}
