package me.washcar.wcnc.domain.member.dao;

import static org.assertj.core.api.AssertionsForClassTypes.*;

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
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.MemberTestHelper;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberTestHelper memberTestHelper;

	@Nested
	@DisplayName("멤버 목록 페이징")
	class memberPage {

		@Test
		@DisplayName("멤버가 한 명인 경우 멤버 조회에 성공한다")
		void should_success_when_singleMember() {
			//given
			Member member = memberTestHelper.makeStaticMember();
			memberRepository.save(member);
			int page = 0;
			int size = 12;

			//when
			Pageable pageable = PageRequest.of(page, size);
			Page<Member> resultPage = memberRepository.findAll(pageable);

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
			assertThat(resultPage.get().findFirst().get().getName()).isEqualTo(member.getName());
		}

		@Test
		@DisplayName("멤버가 없는 경우에도 멤버 조회에 성공한다")
		void should_success_when_zeroMember() {
			//given
			int page = 0;
			int size = 5;

			//when
			Pageable pageable = PageRequest.of(page, size);
			Page<Member> resultPage = memberRepository.findAll(pageable);

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
		@DisplayName("멤버가 존재하는 경우 멤버 조회에 성공한다")
		void should_success_when_memberExist() {
			//given
			Member member = memberTestHelper.makeRandomMember();
			memberRepository.save(member);

			//when
			Optional<Member> result = memberRepository.findByUuid(member.getUuid());

			//then
			assertThat(result).isPresent();
			assertThat(result.get().getUuid()).isEqualTo(member.getUuid());
			assertThat(result.get().getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
		}

		@Test
		@DisplayName("멤버가 존재하지 않는 경우 멤버 조회에 실패한다")
		void should_fail_when_memberNotExist() {
			//given
			//INTENDED-BLANK

			//when
			Optional<Member> result = memberRepository.findByUuid(UUID.randomUUID().toString());

			//then
			assertThat(result).isEmpty();
		}

		@Test
		@DisplayName("잘못된 UUID 양식인 경우 멤버 조회에 실패한다")
		void should_fail_when_wrongUuidFormat() {
			//given
			//INTENDED-BLANK

			//when
			Optional<Member> result = memberRepository.findByUuid(UUID.randomUUID().toString());

			//then
			assertThat(result).isEmpty();
		}

	}

}
