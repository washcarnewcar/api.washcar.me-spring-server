package me.washcar.wcnc.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationError {

	TOKEN_NOT_DELETED("인증번호 기록이 삭제되지 않았습니다."),
	;

	private final String message;

}
