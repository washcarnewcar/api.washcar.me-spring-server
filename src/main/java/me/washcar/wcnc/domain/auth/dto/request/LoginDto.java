package me.washcar.wcnc.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginDto {

	@NotBlank
	private String memberId;

	@NotBlank
	private String password;
	
}
