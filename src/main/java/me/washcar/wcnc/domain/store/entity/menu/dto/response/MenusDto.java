package me.washcar.wcnc.domain.store.entity.menu.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class MenusDto {

	private final List<MenuResponseDto> menus;

	public MenusDto(List<MenuResponseDto> menus) {
		this.menus = menus;
	}

}
