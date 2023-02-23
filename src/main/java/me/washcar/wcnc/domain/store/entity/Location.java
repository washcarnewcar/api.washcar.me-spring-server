package me.washcar.wcnc.domain.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Location {

	@Column(nullable = false)
	private Double latitude;

	@Column(nullable = false)
	private Double longitude;

	private String address;

	private String addressDetail;

	private String wayTo;

}
