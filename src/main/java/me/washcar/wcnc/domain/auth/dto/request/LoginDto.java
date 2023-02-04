package me.washcar.wcnc.domain.auth.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginDto {
	private String userId;
	private String password;
}
