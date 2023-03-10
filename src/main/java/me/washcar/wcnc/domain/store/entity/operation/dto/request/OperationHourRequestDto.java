package me.washcar.wcnc.domain.store.entity.operation.dto.request;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import me.washcar.wcnc.domain.store.entity.operation.entity.OperationHour;

@Getter
public class OperationHourRequestDto {

	@NotNull
	private final LocalTime sundayStartTime;

	@NotNull
	private final LocalTime sundayEndTime;

	@NotNull
	private final LocalTime mondayStartTime;

	@NotNull
	private final LocalTime mondayEndTime;

	@NotNull
	private final LocalTime tuesdayStartTime;

	@NotNull
	private final LocalTime tuesdayEndTime;

	@NotNull
	private final LocalTime wednesdayStartTime;

	@NotNull
	private final LocalTime wednesdayEndTime;

	@NotNull
	private final LocalTime thursdayStartTime;

	@NotNull
	private final LocalTime thursdayEndTime;

	@NotNull
	private final LocalTime fridayStartTime;

	@NotNull
	private final LocalTime fridayEndTime;

	@NotNull
	private final LocalTime saturdayStartTime;

	@NotNull
	private final LocalTime saturdayEndTime;

	@Builder
	@SuppressWarnings("unused")
	private OperationHourRequestDto(LocalTime sundayStartTime, LocalTime sundayEndTime, LocalTime mondayStartTime,
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

	public static OperationHourRequestDto from(OperationHour operationHour) {
		return OperationHourRequestDto.builder()
			.sundayStartTime(operationHour.getSundayStartTime())
			.sundayEndTime(operationHour.getSundayEndTime())
			.mondayStartTime(operationHour.getMondayStartTime())
			.mondayEndTime(operationHour.getMondayEndTime())
			.tuesdayStartTime(operationHour.getTuesdayStartTime())
			.tuesdayEndTime(operationHour.getTuesdayEndTime())
			.wednesdayStartTime(operationHour.getWednesdayStartTime())
			.wednesdayEndTime(operationHour.getWednesdayEndTime())
			.thursdayStartTime(operationHour.getThursdayStartTime())
			.thursdayEndTime(operationHour.getThursdayEndTime())
			.fridayStartTime(operationHour.getFridayStartTime())
			.fridayEndTime(operationHour.getFridayEndTime())
			.saturdayStartTime(operationHour.getSaturdayStartTime())
			.saturdayEndTime(operationHour.getSaturdayEndTime())
			.build();
	}

}
