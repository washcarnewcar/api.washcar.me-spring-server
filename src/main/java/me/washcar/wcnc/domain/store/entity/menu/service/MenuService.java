package me.washcar.wcnc.domain.store.entity.menu.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.domain.store.entity.menu.dao.MenuRepository;
import me.washcar.wcnc.domain.store.entity.menu.dto.request.MenuRequestDto;
import me.washcar.wcnc.domain.store.entity.menu.dto.response.MenuDto;
import me.washcar.wcnc.domain.store.entity.menu.dto.response.MenuListDto;
import me.washcar.wcnc.domain.store.entity.menu.entity.Menu;
import me.washcar.wcnc.domain.store.service.StoreService;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class MenuService {

	public static final int MAX_MENU_NUMBER = 64;

	private final StoreService storeService;

	private final StoreRepository storeRepository;

	private final MenuRepository menuRepository;

	private final ModelMapper modelMapper;

	@Transactional
	public void create(String slug, MenuRequestDto requestDto) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		storeService.checkStoreChangePermit(store);
		if (store.getMenus().size() >= MAX_MENU_NUMBER) {
			throw new BusinessException(BusinessError.EXCEED_STORE_MENU_LIMIT);
		}
		Menu menu = Menu.builder()
			.price(requestDto.getPrice())
			.expectedMinute(requestDto.getExpectedMinute())
			.description(requestDto.getDescription())
			.image(requestDto.getImage())
			.build();
		store.addStoreMenu(menu);
	}

	@Transactional(readOnly = true)
	public MenuListDto getMenusBySlug(String slug) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		List<Menu> menus = store.getMenus();
		List<MenuDto> menuDtos = menus.stream()
			.map(m -> modelMapper.map(m, MenuDto.class))
			.toList();
		return new MenuListDto(menuDtos);
	}

	@Transactional(readOnly = true)
	public MenuDto getMenuByUuid(String uuid) {
		Menu menu = menuRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_MENU_NOT_FOUND));
		return modelMapper.map(menu, MenuDto.class);
	}

	@Transactional
	public void putMenuByUuid(String uuid, MenuRequestDto requestDto) {
		Menu menu = menuRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_MENU_NOT_FOUND));
		Store store = menu.getStore();
		storeService.checkStoreChangePermit(store);
		menu.updateMenu(requestDto.getPrice(),
			requestDto.getExpectedMinute(),
			requestDto.getDescription(),
			requestDto.getImage()
		);
	}

	@Transactional
	public void deleteMenuByUuid(String uuid) {
		Menu menu = menuRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_MENU_NOT_FOUND));
		Store store = menu.getStore();
		storeService.checkStoreChangePermit(store);
		store.deleteStoreMenu(menu);
	}

}
