package me.washcar.wcnc.domain.auth.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.auth.entity.SignupToken;

public interface SignupTokenRepository extends JpaRepository<SignupToken, Long> {

	Optional<SignupToken> findFirstByTelephoneOrderByCreatedDateDesc(String telephone);

	boolean existsByTelephoneAndTokenAndCreatedDateAfter(String telephone, String token, LocalDateTime localDateTime);

	long deleteAllByTelephone(String telephone);
}
