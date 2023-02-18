package me.washcar.wcnc.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessError {

	ID_PASSWORD_AUTH_FAILED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 틀렸습니다."),
	LOGIN_ID_DUPLICATED(HttpStatus.CONFLICT, "중복된 아이디 입니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
	MEMBER_TELEPHONE_DUPLICATED(HttpStatus.CONFLICT, "중복된 휴대폰 번호 입니다."),
	SIGNUP_TOKEN_TOO_FAST(HttpStatus.TOO_MANY_REQUESTS, "잠시 후 다시 시도해주세요."),
	SIGNUP_TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "인증번호가 유효하지 않습니다."),
	;

	private final HttpStatus httpStatus;

	private final String message;

}
