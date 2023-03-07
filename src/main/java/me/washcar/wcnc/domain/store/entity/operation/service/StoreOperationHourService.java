package me.washcar.wcnc.domain.store.entity.operation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.domain.store.entity.operation.dto.request.OperationHourRequestDto;
import me.washcar.wcnc.domain.store.entity.operation.dto.response.OperationHourDto;
import me.washcar.wcnc.domain.store.service.StoreService;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class StoreOperationHourService {

	private final StoreService storeService;

	private final StoreRepository storeRepository;

	@Transactional(readOnly = true)
	public OperationHourDto getOperationHourBySlug(String slug) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		return OperationHourDto.from(store.getStoreOperationHour());
	}

	@Transactional
	public void putOperationHourBySlug(String slug, OperationHourRequestDto requestDto) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		storeService.checkStoreChangePermit(store);
		store.getStoreOperationHour().update(requestDto);
	}
}
