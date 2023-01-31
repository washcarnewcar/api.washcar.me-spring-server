package me.washcar.wcnc.domain.store.operationHours;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import me.washcar.wcnc.domain.store.Store;
import me.washcar.wcnc.global.entity.UuidEntity;

@Entity
@Getter
@Table(indexes = @Index(name = "uuid_store_operation_exception_index", columnList = "uuid"))
public class StoreOperationException extends UuidEntity {

	@Column(nullable = false)
	private LocalDateTime startDateTime;

	@Column(nullable = false)
	private LocalDateTime endDateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	private Store store;

}
