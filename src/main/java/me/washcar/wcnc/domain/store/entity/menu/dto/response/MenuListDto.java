package me.washcar.wcnc.domain.store.entity.menu.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class MenuListDto {

	private final List<MenuDto> menus;

	public MenuListDto(List<MenuDto> menus) {
		this.menus = menus;
	}

}
