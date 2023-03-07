package me.washcar.wcnc.domain.store.entity.operation.dto.request;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class OperationHourRequestDto {

	@NotNull
	private LocalTime sundayStartTime;

	@NotNull
	private LocalTime sundayEndTime;

	@NotNull
	private LocalTime mondayStartTime;

	@NotNull
	private LocalTime mondayEndTime;

	@NotNull
	private LocalTime tuesdayStartTime;

	@NotNull
	private LocalTime tuesdayEndTime;

	@NotNull
	private LocalTime wednesdayStartTime;

	@NotNull
	private LocalTime wednesdayEndTime;

	@NotNull
	private LocalTime thursdayStartTime;

	@NotNull
	private LocalTime thursdayEndTime;

	@NotNull
	private LocalTime fridayStartTime;

	@NotNull
	private LocalTime fridayEndTime;

	@NotNull
	private LocalTime saturdayStartTime;

	@NotNull
	private LocalTime saturdayEndTime;

}
