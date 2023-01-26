package me.washcar.wcnc.domain.member.dao;

import me.washcar.wcnc.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findAll(Pageable pageable);

    Optional<Member> findByUuid(String uuid);

}
