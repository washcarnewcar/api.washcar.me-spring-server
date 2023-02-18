package me.washcar.wcnc.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class LoginDto {

	@NotBlank
	private String loginId;

	@NotBlank
	private String loginPassword;

}
