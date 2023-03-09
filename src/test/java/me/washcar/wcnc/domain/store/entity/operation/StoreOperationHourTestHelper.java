package me.washcar.wcnc.domain.store.entity.operation;

import java.time.LocalTime;

import me.washcar.wcnc.domain.store.entity.operation.dto.request.OperationHourRequestDto;

public class StoreOperationHourTestHelper {

	public OperationHourRequestDto makeStaticOperationHourRequestDto() {

		return OperationHourRequestDto
			.builder()
			.sundayStartTime(LocalTime.of(10, 10, 0))
			.sundayEndTime(LocalTime.of(20, 10, 0))
			.mondayStartTime(LocalTime.of(11, 20, 0))
			.mondayEndTime(LocalTime.of(21, 20, 0))
			.tuesdayStartTime(LocalTime.of(12, 30, 0))
			.tuesdayEndTime(LocalTime.of(22, 30, 0))
			.wednesdayStartTime(LocalTime.of(13, 40, 0))
			.wednesdayEndTime(LocalTime.of(23, 40, 0))
			.thursdayStartTime(LocalTime.of(14, 15, 0))
			.thursdayEndTime(LocalTime.of(17, 15, 0))
			.fridayStartTime(LocalTime.of(15, 15, 0))
			.fridayEndTime(LocalTime.of(18, 15, 0))
			.saturdayStartTime(LocalTime.of(16, 15, 0))
			.saturdayEndTime(LocalTime.of(19, 15, 0))
			.build();
	}

	public OperationHourRequestDto makeBadOperationHourRequestDto() {
		return OperationHourRequestDto
			.builder()
			.sundayStartTime(LocalTime.of(10, 10, 0))
			.sundayEndTime(LocalTime.of(20, 10, 0))
			.mondayStartTime(LocalTime.of(11, 20, 0))
			.mondayEndTime(LocalTime.of(21, 20, 0))
			.tuesdayStartTime(LocalTime.of(12, 30, 0))
			.tuesdayEndTime(LocalTime.of(22, 30, 0))
			.wednesdayStartTime(LocalTime.of(23, 40, 0)) // conflict
			.wednesdayEndTime(LocalTime.of(21, 40, 0)) // conflict
			.thursdayStartTime(LocalTime.of(14, 15, 0))
			.thursdayEndTime(LocalTime.of(17, 15, 0))
			.fridayStartTime(LocalTime.of(15, 15, 0))
			.fridayEndTime(LocalTime.of(18, 15, 0))
			.saturdayStartTime(LocalTime.of(16, 15, 0))
			.saturdayEndTime(LocalTime.of(19, 15, 0))
			.build();
	}
}
