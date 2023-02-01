package me.washcar.wcnc.domain.member.dao;

import static me.washcar.wcnc.domain.member.MemberRole.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.domain.member.MemberAuthenticationType;
import me.washcar.wcnc.domain.member.MemberStatus;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired
	private MemberRepository underTest;

	@Nested
	@DisplayName("멤버 목록 페이징")
	class memberPage {

		@Test
		@DisplayName("멤버가 한 명인 경우 성공한다")
		void should_success_when_singleMember() {
			//given
			Member member = Member.builder()
				.name("Gilteun")
				.memberRole(USER)
				.memberStatus(MemberStatus.ACTIVE)
				.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
				.password("randomPassword")
				.telephone("01077778888")
				.stores(new ArrayList<>())
				.reservations(new ArrayList<>())
				.build();
			underTest.save(member);
			int page = 0;
			int size = 12;

			//when
			Pageable pageable = PageRequest.of(page, size);
			Page<Member> resultPage = underTest.findAll(pageable);

			//then
			assertThat(resultPage.getPageable()).isEqualTo(pageable);
			assertThat(resultPage.getTotalElements()).isEqualTo(1L);
			assertThat(resultPage.getTotalPages()).isEqualTo(1L);
			assertThat(resultPage.getNumber()).isEqualTo(page);
			assertThat(resultPage.getSize()).isEqualTo(size);
			assertThat(resultPage.hasNext()).isFalse();
			assertThat(resultPage.isFirst()).isTrue();

			assertThat(resultPage.get().findFirst().isPresent()).isTrue();
			assertThat(resultPage.get().findFirst().get()).isEqualTo(member);
			assertThat(resultPage.get().findFirst().get().getName()).isEqualTo("Gilteun");
		}

		@Test
		@DisplayName("멤버가 없는 경우에도 성공한다")
		void should_success_when_zeroMember() {
			//given
			int page = 0;
			int size = 5;

			//when
			Pageable pageable = PageRequest.of(page, size);
			Page<Member> resultPage = underTest.findAll(pageable);

			//then
			assertThat(resultPage.getPageable()).isEqualTo(pageable);
			assertThat(resultPage.getTotalElements()).isEqualTo(0L);
			assertThat(resultPage.getTotalPages()).isEqualTo(0L);
			assertThat(resultPage.getNumber()).isEqualTo(page);
			assertThat(resultPage.getSize()).isEqualTo(size);
			assertThat(resultPage.hasNext()).isFalse();
			assertThat(resultPage.isFirst()).isTrue();
		}

	}

	@Nested
	@DisplayName("멤버 UUID 접근")
	class memberUuid {

		@Test
		@DisplayName("멤버가 존재하는 경우 성공한다")
		void should_success_when_memberExist() {
			//given
			Member member = Member.builder()
				.name("Gilteun")
				.memberRole(USER)
				.memberStatus(MemberStatus.ACTIVE)
				.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
				.password("randomPassword")
				.telephone("01077778888")
				.stores(new ArrayList<>())
				.reservations(new ArrayList<>())
				.build();
			underTest.save(member);

			//when
			Optional<Member> result = underTest.findByUuid(member.getUuid());

			//then
			assertThat(result.isPresent()).isTrue();
			assertThat(result.get().getUuid()).isEqualTo(member.getUuid());
			assertThat(result.get().getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
		}

		@Test
		@DisplayName("멤버가 존재하지 않는 경우 실패한다")
		void should_fail_when_memberNotExist() {
			//given

			//when
			Optional<Member> result = underTest.findByUuid(UUID.randomUUID().toString());

			//then
			assertThat(result.isPresent()).isFalse();
		}

		@Test
		@DisplayName("잘못된 UUID 양식인 경우 실패한다")
		void should_fail_when_wrongUuidFormat() {
			//given

			//when
			Optional<Member> result = underTest.findByUuid("bad-uuid-example");

			//then
			assertThat(result.isPresent()).isFalse();
		}

	}

}
