package me.washcar.wcnc.domain.auth.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;
import me.washcar.wcnc.global.def.Regex;

@Getter
@ToString
public class CheckMemberIdDto {
	@Pattern(regexp = Regex.MEMBER_ID, message = Regex.MEMBER_ID_MSG)
	private String memberId;
}
