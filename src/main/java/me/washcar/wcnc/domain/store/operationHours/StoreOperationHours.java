package me.washcar.wcnc.domain.store.operationHours;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import me.washcar.wcnc.domain.store.Store;
import me.washcar.wcnc.global.entity.BaseEntity;

@Entity
@Getter
public class StoreOperationHours extends BaseEntity {

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

	@OneToOne
	private Store store;

}
