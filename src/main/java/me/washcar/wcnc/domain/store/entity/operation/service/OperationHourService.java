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
public class OperationHourService {

	private final StoreService storeService;

	private final StoreRepository storeRepository;

	@Transactional(readOnly = true)
	public OperationHourDto getOperationHourBySlug(String slug) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		return OperationHourDto.from(store.getOperationHour());
	}

	@Transactional
	public void putOperationHourBySlug(String slug, OperationHourRequestDto requestDto) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		storeService.checkStoreChangePermit(store);
		if (requestDto.getSundayStartTime().isAfter(requestDto.getSundayEndTime()) ||
			requestDto.getMondayStartTime().isAfter(requestDto.getMondayEndTime()) ||
			requestDto.getTuesdayStartTime().isAfter(requestDto.getTuesdayEndTime()) ||
			requestDto.getWednesdayStartTime().isAfter(requestDto.getWednesdayEndTime()) ||
			requestDto.getThursdayStartTime().isAfter(requestDto.getThursdayEndTime()) ||
			requestDto.getFridayStartTime().isAfter(requestDto.getFridayEndTime()) ||
			requestDto.getSaturdayStartTime().isAfter(requestDto.getSaturdayEndTime())
		) {
			throw new BusinessException(BusinessError.BAD_OPERATION_HOUR_REQUEST);
		}
		store.getOperationHour().update(requestDto);
	}
}
