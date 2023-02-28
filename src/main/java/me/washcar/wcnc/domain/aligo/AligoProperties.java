package me.washcar.wcnc.domain.aligo;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "aligo")
@RequiredArgsConstructor
@Getter
public class AligoProperties {
	private final String userId;
	private final String sender;
	private final String apiKey;
	private final String testMode;
}
