package me.washcar.wcnc.domain.store.entity.operation.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.domain.store.entity.operation.dao.OperationHolidayRepository;
import me.washcar.wcnc.domain.store.entity.operation.dto.request.HolidayRequestDto;
import me.washcar.wcnc.domain.store.entity.operation.dto.response.HolidayDto;
import me.washcar.wcnc.domain.store.entity.operation.dto.response.HolidayListDto;
import me.washcar.wcnc.domain.store.entity.operation.entity.Holiday;
import me.washcar.wcnc.domain.store.service.StoreService;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class OperationHolidayService {

	private final StoreService storeService;

	private final StoreRepository storeRepository;

	private final OperationHolidayRepository operationHolidayRepository;

	private final ModelMapper modelMapper;

	@Transactional
	public void create(String slug, HolidayRequestDto requestDto) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		storeService.checkStoreChangePermit(store);
		if (requestDto.getStartDateTime().isAfter(requestDto.getEndDateTime())) {
			throw new BusinessException(BusinessError.BAD_OPERATION_HOLIDAY_REQUEST);
		}
		Holiday holiday = Holiday.builder()
			.startDateTime(requestDto.getStartDateTime())
			.endDateTime(requestDto.getEndDateTime())
			.build();
		store.addStoreHoliday(holiday);
	}

	@Transactional(readOnly = true)
	public HolidayListDto getEveryHolidayBySlug(String slug) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		List<Holiday> operationHolidays = store.getHolidays();
		List<HolidayDto> holidayDtos = operationHolidays.stream()
			.map(m -> modelMapper.map(m, HolidayDto.class))
			.toList();
		return new HolidayListDto(holidayDtos);
	}

	@Transactional(readOnly = true)
	public HolidayDto getHolidayByUuid(String uuid) {
		Holiday holiday = operationHolidayRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_HOLIDAY_NOT_FOUND));
		return modelMapper.map(holiday, HolidayDto.class);
	}

	@Transactional
	public void putHolidayByUuid(String uuid, HolidayRequestDto requestDto) {
		Holiday holiday = operationHolidayRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_HOLIDAY_NOT_FOUND));
		Store store = holiday.getStore();
		storeService.checkStoreChangePermit(store);
		holiday.update(requestDto.getStartDateTime(),
			requestDto.getEndDateTime()
		);
	}

	@Transactional
	public void deleteHolidayByUuid(String uuid) {
		Holiday holiday = operationHolidayRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_HOLIDAY_NOT_FOUND));
		Store store = holiday.getStore();
		storeService.checkStoreChangePermit(store);
		store.deleteStoreHoliday(holiday);
	}

}
