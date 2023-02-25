package me.washcar.wcnc.domain.member.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;

@Getter
@SuppressWarnings("unused")
public class MemberPutRequestDto {

	@Pattern(regexp = Regex.NAME, message = RegexMessage.NAME)
	private String nickname;

}
