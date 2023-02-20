package me.washcar.wcnc.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationError {

	TOKEN_NOT_DELETED("인증번호 기록이 삭제되지 않았습니다."),
	ROLE_NOT_FOUND("해당 유저가 ROLE을 가지고 있지 않습니다."),
	ROLE_TYPE_ERROR("해당 유저가 정의되지 않은 ROLE을 가지고 있습니다."),
	MULTIPLE_ROLE_FOUND("유저가 두 개 이상의 ROLE을 가지고 있습니다."),
	;

	private final String message;

}
