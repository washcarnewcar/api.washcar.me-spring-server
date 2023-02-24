package me.washcar.wcnc.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationError {

	PIN_NUMBER_NOT_DELETED("인증번호 기록이 삭제되지 않았습니다."),
	MESSAGE_RESPONSE_CAN_NOT_PARSE("메시지를 전송한 후 응답을 파싱하지 못했습니다."),
	MESSAGE_SEND_ERROR("메시지를 전송하지 못했습니다."),
	;

	private final String message;

}
