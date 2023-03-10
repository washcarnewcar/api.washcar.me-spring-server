package me.washcar.wcnc.domain.store.entity.operation.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.store.entity.operation.entity.Holiday;

public interface OperationHolidayRepository extends JpaRepository<Holiday, Long> {

	Optional<Holiday> findByUuid(String uuid);

}
