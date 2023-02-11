package me.washcar.wcnc.domain.auth.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import me.washcar.wcnc.global.def.Regex;

@Getter
public class SignupDto {
	@Pattern(regexp = Regex.MEMBER_ID, message = Regex.MEMBER_ID_MSG)
	private String memberId;

	@Pattern(regexp = Regex.NAME, message = Regex.NAME_MSG)
	private String name;

	@Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_MSG)
	private String password;

	@Pattern(regexp = Regex.TELEPHONE, message = Regex.TELEPHONE_MSG)
	private String telephone;

	@Pattern(regexp = Regex.TOKEN, message = Regex.TOKEN_MSG)
	private String token;
}
