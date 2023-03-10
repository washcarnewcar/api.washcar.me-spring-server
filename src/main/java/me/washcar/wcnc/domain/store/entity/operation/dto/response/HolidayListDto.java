package me.washcar.wcnc.domain.store.entity.operation.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class HolidayListDto {

	private final List<HolidayDto> holidays;

	public HolidayListDto(List<HolidayDto> holidays) {
		this.holidays = holidays;
	}

}
