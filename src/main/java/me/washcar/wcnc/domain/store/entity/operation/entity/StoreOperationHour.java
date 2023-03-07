package me.washcar.wcnc.domain.store.entity.operation.entity;

import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.domain.store.entity.operation.dto.request.OperationHourRequestDto;
import me.washcar.wcnc.global.entity.BaseEntity;

@Entity
@Getter
public class StoreOperationHour extends BaseEntity {

	@Column(nullable = false)
	private LocalTime sundayStartTime;

	@Column(nullable = false)
	private LocalTime sundayEndTime;

	@Column(nullable = false)
	private LocalTime mondayStartTime;

	@Column(nullable = false)
	private LocalTime mondayEndTime;

	@Column(nullable = false)
	private LocalTime tuesdayStartTime;

	@Column(nullable = false)
	private LocalTime tuesdayEndTime;

	@Column(nullable = false)
	private LocalTime wednesdayStartTime;

	@Column(nullable = false)
	private LocalTime wednesdayEndTime;

	@Column(nullable = false)
	private LocalTime thursdayStartTime;

	@Column(nullable = false)
	private LocalTime thursdayEndTime;

	@Column(nullable = false)
	private LocalTime fridayStartTime;

	@Column(nullable = false)
	private LocalTime fridayEndTime;

	@Column(nullable = false)
	private LocalTime saturdayStartTime;

	@Column(nullable = false)
	private LocalTime saturdayEndTime;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "storeOperationHour")
	private Store store;

	public StoreOperationHour() {
		LocalTime defaultBeginningTime = LocalTime.of(9, 0, 0);
		LocalTime defaultEndTime = LocalTime.of(18, 0, 0);

		this.sundayStartTime = defaultBeginningTime;
		this.sundayEndTime = defaultEndTime;
		this.mondayStartTime = defaultBeginningTime;
		this.mondayEndTime = defaultEndTime;
		this.tuesdayStartTime = defaultBeginningTime;
		this.tuesdayEndTime = defaultEndTime;
		this.wednesdayStartTime = defaultBeginningTime;
		this.wednesdayEndTime = defaultEndTime;
		this.thursdayStartTime = defaultBeginningTime;
		this.thursdayEndTime = defaultEndTime;
		this.fridayStartTime = defaultBeginningTime;
		this.fridayEndTime = defaultEndTime;
		this.saturdayStartTime = defaultBeginningTime;
		this.saturdayEndTime = defaultEndTime;
	}

	public void update(OperationHourRequestDto requestDto) {
		this.sundayStartTime = requestDto.getSundayStartTime();
		this.sundayEndTime = requestDto.getSundayEndTime();
		this.mondayStartTime = requestDto.getMondayStartTime();
		this.mondayEndTime = requestDto.getMondayEndTime();
		this.tuesdayStartTime = requestDto.getTuesdayStartTime();
		this.tuesdayEndTime = requestDto.getTuesdayEndTime();
		this.wednesdayStartTime = requestDto.getWednesdayStartTime();
		this.wednesdayEndTime = requestDto.getWednesdayEndTime();
		this.thursdayStartTime = requestDto.getThursdayStartTime();
		this.thursdayEndTime = requestDto.getThursdayEndTime();
		this.fridayStartTime = requestDto.getFridayStartTime();
		this.fridayEndTime = requestDto.getFridayEndTime();
		this.saturdayStartTime = requestDto.getSaturdayStartTime();
		this.saturdayEndTime = requestDto.getSaturdayEndTime();
	}
}
