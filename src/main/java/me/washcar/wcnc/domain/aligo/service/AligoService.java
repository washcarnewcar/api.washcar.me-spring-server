package me.washcar.wcnc.domain.aligo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.washcar.wcnc.domain.aligo.AligoProperties;
import me.washcar.wcnc.domain.aligo.dto.response.MessageSendResponseDto;
import me.washcar.wcnc.global.error.ApplicationError;
import me.washcar.wcnc.global.error.ApplicationException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AligoService {
	private final ObjectMapper objectMapper;
	private final AligoProperties aligoProperties;

	public void sendMessageSingle(String message, String receiver) {
		sendMessage(message, List.of(receiver));
	}

	public void sendMessage(String message, List<String> receivers) {

		String receiverJoin = String.join(",", receivers);

		WebClient client = WebClient.builder()
			.baseUrl("https://apis.aligo.in")
			.build();

		Mono<String> responseMono = client.post()
			.uri("/send/")
			.body(BodyInserters.fromFormData("key", aligoProperties.getApiKey())
				.with("user_id", aligoProperties.getUserId())
				.with("sender", aligoProperties.getSender())
				.with("receiver", receiverJoin)
				.with("msg", message)
				.with("testmode_yn", aligoProperties.getTestMode()))
			.retrieve()
			.bodyToMono(String.class);

		responseMono.subscribe(this::sendMessageResponse);
	}

	private void sendMessageResponse(String response) {
		MessageSendResponseDto responseDto;
		try {
			responseDto = objectMapper.readValue(response, MessageSendResponseDto.class);
		} catch (JsonProcessingException e) {
			log.error(e.toString());
			throw new ApplicationException(ApplicationError.MESSAGE_RESPONSE_CAN_NOT_PARSE);
		}

		if (responseDto.getResultCode() < 0) {
			log.error("메시지 전송 후 받은 응답: {}", responseDto);
			throw new ApplicationException(ApplicationError.MESSAGE_SEND_ERROR);
		}
	}
}
