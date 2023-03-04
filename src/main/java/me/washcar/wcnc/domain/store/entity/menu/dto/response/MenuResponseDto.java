package me.washcar.wcnc.domain.store.entity.menu.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuResponseDto {

	private int price;

	private int expectedMinute;

	private String description;

	private String image;

	private String uuid;

}
