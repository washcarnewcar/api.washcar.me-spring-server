package me.washcar.wcnc.domain.auth.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.auth.entity.SignupPinNumber;

public interface SignupPinNumberRepository extends JpaRepository<SignupPinNumber, Long> {

	Optional<SignupPinNumber> findFirstByTelephoneOrderByCreatedDateDesc(String telephone);

	boolean existsByTelephoneAndPinNumberAndCreatedDateAfter(
		String telephone, String pinNumber, LocalDateTime localDateTime);

	long deleteAllByTelephone(String telephone);
}
