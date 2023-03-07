package me.washcar.wcnc.domain.store.entity.operation.dto.response;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;
import me.washcar.wcnc.domain.store.entity.operation.entity.StoreOperationHour;

@Getter
public class OperationHourDto {

	private final LocalTime sundayStartTime;

	private final LocalTime sundayEndTime;

	private final LocalTime mondayStartTime;

	private final LocalTime mondayEndTime;

	private final LocalTime tuesdayStartTime;

	private final LocalTime tuesdayEndTime;

	private final LocalTime wednesdayStartTime;

	private final LocalTime wednesdayEndTime;

	private final LocalTime thursdayStartTime;

	private final LocalTime thursdayEndTime;

	private final LocalTime fridayStartTime;

	private final LocalTime fridayEndTime;

	private final LocalTime saturdayStartTime;

	private final LocalTime saturdayEndTime;

	@Builder
	@SuppressWarnings("unused")
	private OperationHourDto(LocalTime sundayStartTime, LocalTime sundayEndTime, LocalTime mondayStartTime,
		LocalTime mondayEndTime, LocalTime tuesdayStartTime, LocalTime tuesdayEndTime, LocalTime wednesdayStartTime,
		LocalTime wednesdayEndTime, LocalTime thursdayStartTime, LocalTime thursdayEndTime, LocalTime fridayStartTime,
		LocalTime fridayEndTime, LocalTime saturdayStartTime, LocalTime saturdayEndTime) {

		this.sundayStartTime = sundayStartTime;
		this.sundayEndTime = sundayEndTime;
		this.mondayStartTime = mondayStartTime;
		this.mondayEndTime = mondayEndTime;
		this.tuesdayStartTime = tuesdayStartTime;
		this.tuesdayEndTime = tuesdayEndTime;
		this.wednesdayStartTime = wednesdayStartTime;
		this.wednesdayEndTime = wednesdayEndTime;
		this.thursdayStartTime = thursdayStartTime;
		this.thursdayEndTime = thursdayEndTime;
		this.fridayStartTime = fridayStartTime;
		this.fridayEndTime = fridayEndTime;
		this.saturdayStartTime = saturdayStartTime;
		this.saturdayEndTime = saturdayEndTime;

	}

	public static OperationHourDto from(StoreOperationHour storeOperationHour) {
		return OperationHourDto.builder()
			.sundayStartTime(storeOperationHour.getSundayStartTime())
			.sundayEndTime(storeOperationHour.getSundayEndTime())
			.mondayStartTime(storeOperationHour.getMondayStartTime())
			.mondayEndTime(storeOperationHour.getMondayEndTime())
			.tuesdayStartTime(storeOperationHour.getTuesdayStartTime())
			.tuesdayEndTime(storeOperationHour.getTuesdayEndTime())
			.wednesdayStartTime(storeOperationHour.getWednesdayStartTime())
			.wednesdayEndTime(storeOperationHour.getWednesdayEndTime())
			.thursdayStartTime(storeOperationHour.getThursdayStartTime())
			.thursdayEndTime(storeOperationHour.getThursdayEndTime())
			.fridayStartTime(storeOperationHour.getFridayStartTime())
			.fridayEndTime(storeOperationHour.getFridayEndTime())
			.saturdayStartTime(storeOperationHour.getSaturdayStartTime())
			.saturdayEndTime(storeOperationHour.getSaturdayEndTime())
			.build();
	}

}
