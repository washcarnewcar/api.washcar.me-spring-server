package me.washcar.wcnc.domain.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.member.OAuth;

public interface OAuthRepository extends JpaRepository<OAuth, Long> {
	Optional<OAuth> findByProviderAndProviderId(String provider, String providerId);
}
