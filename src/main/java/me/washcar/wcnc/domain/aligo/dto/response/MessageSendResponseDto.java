package me.washcar.wcnc.domain.aligo.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
@AllArgsConstructor
public class MessageSendResponseDto {
	private int resultCode;
	private String message;
	private int msgId;
	private int successCnt;
	private int errorCnt;
	private String msgType;
}
