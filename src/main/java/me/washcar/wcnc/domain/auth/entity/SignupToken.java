package me.washcar.wcnc.domain.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.washcar.wcnc.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupToken extends BaseEntity {

	@Column(nullable = false)
	private String telephone;

	@Column(nullable = false)
	private String token;

}
