package me.washcar.wcnc.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessError {

	ID_PASSWORD_AUTH_FAILED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 틀렸습니다."),
	MEMBER_ID_DUPLICATED(HttpStatus.CONFLICT, "중복된 아이디 입니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
	MEMBER_TEL_DUPLICATED(HttpStatus.CONFLICT, "중복된 휴대폰 번호 입니다."),
	TOO_FAST_TOKEN(HttpStatus.TOO_MANY_REQUESTS, "잠시 후 다시 시도해주세요."),
	TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "인증번호가 유효하지 않습니다."),
	TOKEN_NOT_DELETED(HttpStatus.INTERNAL_SERVER_ERROR, "인증번호 기록이 삭제되지 않았습니다.");

	private final HttpStatus httpStatus;

	private final String message;

}
