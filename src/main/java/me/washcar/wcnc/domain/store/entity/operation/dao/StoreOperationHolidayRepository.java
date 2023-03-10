package me.washcar.wcnc.domain.store.entity.operation.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.store.entity.operation.entity.StoreOperationHoliday;

public interface StoreOperationHolidayRepository extends JpaRepository<StoreOperationHoliday, Long> {

	Optional<StoreOperationHoliday> findByUuid(String uuid);

}
