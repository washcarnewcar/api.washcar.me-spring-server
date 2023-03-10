package me.washcar.wcnc.domain.member.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Page<Member> findAll(Pageable pageable);

	Optional<Member> findByUuid(String uuid);

	Optional<Member> findByLoginId(String loginId);

	boolean existsByLoginId(String loginId);

	boolean existsByTelephone(String telephone);
}
