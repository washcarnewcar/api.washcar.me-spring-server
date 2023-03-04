package me.washcar.wcnc.domain.store.entity.menu;

import me.washcar.wcnc.domain.store.entity.menu.dto.request.MenuRequestDto;

public class StoreMenuTestHelper {
	
	public MenuRequestDto makeStaticMenuRequestDto() {
		return MenuRequestDto
			.builder()
			.price(35000)
			.expectedMinute(60)
			.description("testMenuDescription")
			.image("/store/test/menu01")
			.build();
	}

}
