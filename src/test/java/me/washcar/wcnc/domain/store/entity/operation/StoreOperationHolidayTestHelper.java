package me.washcar.wcnc.domain.store.entity.operation;

import java.time.LocalDateTime;

import me.washcar.wcnc.domain.store.entity.operation.dto.request.HolidayRequestDto;

public class StoreOperationHolidayTestHelper {
	public HolidayRequestDto makeBadOperationHolidayRequestDto() {
		return HolidayRequestDto.builder()
			.startDateTime(LocalDateTime.of(1970, 1, 1, 12, 30, 0)) //conflict
			.endDateTime(LocalDateTime.of(1970, 1, 1, 10, 30, 0)) //conflict
			.build();
	}
}
