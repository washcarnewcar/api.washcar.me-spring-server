package me.washcar.wcnc.domain.store.entity.operation.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HolidayRequestDto {

	@NotNull
	private LocalDateTime startDateTime;

	@NotNull
	private LocalDateTime endDateTime;

}
