package me.washcar.wcnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan    // ConfigurationProperties가 붙은 클래스에 properties에서 값을 찾아 주입합니다.
@EnableEncryptableProperties    // properties 중 ENC가 붙은 속성값들을 Decoding합니다.
public class WcncApplication {

	public static void main(String[] args) {
		SpringApplication.run(WcncApplication.class, args);
	}

}
