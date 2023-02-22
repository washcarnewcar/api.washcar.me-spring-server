package me.washcar.wcnc.domain.aligo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "aligo")
@Getter
@Setter
public class AligoProperties {
	private String userId;
	private String sender;
	private String apiKey;
	private String testMode;
}
