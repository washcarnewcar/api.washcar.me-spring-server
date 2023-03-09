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
	MEMBER_TELEPHONE_DUPLICATED(HttpStatus.CONFLICT, "이미 가입된 휴대폰 번호입니다."),
	SIGNUP_PIN_NUMBER_TOO_FAST(HttpStatus.TOO_MANY_REQUESTS, "잠시 후 다시 시도해주세요."),
	SIGNUP_PIN_NUMBER_NOT_VALID(HttpStatus.BAD_REQUEST, "인증번호가 유효하지 않습니다."),
	NOT_KOREAN_TELEPHONE(HttpStatus.BAD_REQUEST, "국내 전화번호로만 가입 가능합니다."),
	OAUTH_NOT_SUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, "지원되지 않는 서비스 제공자 입니다."),
	MEMBER_DISABLED(HttpStatus.UNAUTHORIZED, "사용자의 계정이 비활성화 되었습니다."),
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "세차장을 찾을 수 없습니다."),
	STORE_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 해당 SLUG를 가진 세차장이 존재합니다."),
	EXCEED_STORE_IMAGE_LIMIT(HttpStatus.CONFLICT, "이미지 최대 등록 가능 횟수를 초과했습니다."),
	EXCEED_STORE_MENU_LIMIT(HttpStatus.CONFLICT, "메뉴 최대 등록 가능 횟수를 초과했습니다."),
	FORBIDDEN_STORE_CHANGE(HttpStatus.FORBIDDEN, "해당 세차장의 정보 수정을 할 권한이 없습니다."),
	FORBIDDEN_MEMBER_CHANGE(HttpStatus.FORBIDDEN, "해당 멤버의 정보 수정을 할 권한이 없습니다."),
	STORE_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "세차장 이미지를 찾을 수 없습니다."),
	STORE_MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "세차장 메뉴를 찾을 수 없습니다."),
	MEMBER_ROLE_NOT_MATCHED(HttpStatus.CONFLICT, "사용자의 정보가 변경되었습니다."),
	BAD_OPERATION_HOUR_REQUEST(HttpStatus.CONFLICT, "세차장 운영 시작 시간은, 운영 종료 시간 이전이어야 합니다."),
	;

	private final HttpStatus httpStatus;

	private final String message;

}
