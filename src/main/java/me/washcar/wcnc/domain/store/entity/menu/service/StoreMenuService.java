package me.washcar.wcnc.domain.store.entity.menu.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.domain.store.entity.menu.dao.StoreMenuRepository;
import me.washcar.wcnc.domain.store.entity.menu.dto.request.MenuRequestDto;
import me.washcar.wcnc.domain.store.entity.menu.dto.response.MenuResponseDto;
import me.washcar.wcnc.domain.store.entity.menu.dto.response.MenusDto;
import me.washcar.wcnc.domain.store.entity.menu.entity.StoreMenu;
import me.washcar.wcnc.domain.store.service.StoreService;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class StoreMenuService {

	public static final int MAX_MENU_NUMBER = 64;

	private final StoreService storeService;

	private final StoreRepository storeRepository;

	private final StoreMenuRepository storeMenuRepository;

	private final ModelMapper modelMapper;

	@Transactional
	public void create(String slug, MenuRequestDto requestDto) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		storeService.checkStoreChangePermit(store);
		if (store.getStoreMenus().size() >= MAX_MENU_NUMBER) {
			throw new BusinessException(BusinessError.EXCEED_STORE_MENU_LIMIT);
		}
		StoreMenu storeMenu = StoreMenu.builder()
			.price(requestDto.getPrice())
			.expectedMinute(requestDto.getExpectedMinute())
			.description(requestDto.getDescription())
			.image(requestDto.getImage())
			.build();
		store.addStoreMenu(storeMenu);
	}

	@Transactional(readOnly = true)
	public MenusDto getMenusBySlug(String slug) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		List<StoreMenu> storeMenus = store.getStoreMenus();
		List<MenuResponseDto> menuResponseDtos = storeMenus.stream()
			.map(m -> modelMapper.map(m, MenuResponseDto.class))
			.toList();
		return new MenusDto(menuResponseDtos);
	}

	@Transactional(readOnly = true)
	public MenuResponseDto getMenuByUuid(String uuid) {
		StoreMenu storeMenu = storeMenuRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_MENU_NOT_FOUND));
		return modelMapper.map(storeMenu, MenuResponseDto.class);
	}

	@Transactional
	public void putMenuByUuid(String uuid, MenuRequestDto requestDto) {
		StoreMenu storeMenu = storeMenuRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_MENU_NOT_FOUND));
		Store store = storeMenu.getStore();
		storeService.checkStoreChangePermit(store);
		storeMenu.updateMenu(requestDto.getPrice(),
			requestDto.getExpectedMinute(),
			requestDto.getDescription(),
			requestDto.getImage()
		);
	}

	@Transactional
	public void deleteMenuByUuid(String uuid) {
		StoreMenu storeMenu = storeMenuRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_MENU_NOT_FOUND));
		Store store = storeMenu.getStore();
		storeService.checkStoreChangePermit(store);
		store.deleteStoreMenu(storeMenu);
	}

}
