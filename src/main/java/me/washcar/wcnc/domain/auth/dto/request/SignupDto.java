package me.washcar.wcnc.domain.auth.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;

@Getter
public class SignupDto {

	@Pattern(regexp = Regex.MEMBER_ID, message = RegexMessage.MEMBER_ID)
	private String memberId;

	@Pattern(regexp = Regex.NAME, message = RegexMessage.NAME)
	private String nickname;

	@Pattern(regexp = Regex.PASSWORD, message = RegexMessage.PASSWORD)
	private String password;

	@Pattern(regexp = Regex.TELEPHONE, message = RegexMessage.TELEPHONE)
	private String telephone;

	@Pattern(regexp = Regex.TOKEN, message = RegexMessage.TOKEN)
	private String token;
	
}