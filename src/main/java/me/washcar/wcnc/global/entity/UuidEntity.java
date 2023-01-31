package me.washcar.wcnc.global.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;

/**
 * 개발 편의를 위해 BINARY 가 아닌 String 의 형태로 UUID 를 저장합니다
 * 외부에 ID가 노출되는 엔티티의 경우 트래픽 노출 방지를 위해 UuidEntity 를 extends 하여 사용하고 @Index(columnList = "uuid") 를 설정하여야 합니다
 */

@MappedSuperclass
@Getter
public class UuidEntity extends BaseEntity {

	@Column(nullable = false, length = 36)
	private String uuid;

	@PrePersist
	private void onCreate() {
		this.uuid = UUID.randomUUID().toString();
	}

}
