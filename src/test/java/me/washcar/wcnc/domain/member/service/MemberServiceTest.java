package me.washcar.wcnc.domain.member.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.MemberTestHelper;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.global.error.BusinessException;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private ModelMapper modelMapper;
	private MemberTestHelper memberTestHelper;
	private MemberService memberService;

	@BeforeEach
	void setUp() {
		memberService = new MemberService(memberRepository, modelMapper);
		memberTestHelper = new MemberTestHelper();
	}

	@Test
	@Disabled
	void postMember() {
		//TODO 미구현
	}

	@Nested
	@DisplayName("멤버 목록 가져오기")
	class getMemberList {

		@Test
		@DisplayName("page와 size가 정상 범위인 경우 Pageable역시 동일한 값을 가진다")
		void should_pageableIsCorrect_when_pageAndSizesAreInCorrectRange() {
			//given
			int page = 0;
			int size = 5;
			Pageable pageable = PageRequest.of(page, size);
			Page<Member> memberPage = Mockito.mock(Page.class);
			when(memberRepository.findAll(pageable)).thenReturn(memberPage);

			//when
			memberService.getMemberList(page, size);

			//then
			ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
			verify(memberRepository).findAll(pageableArgumentCaptor.capture());
			Pageable capturedPageable = pageableArgumentCaptor.getValue();
			assertThat(capturedPageable.getPageNumber()).isEqualTo(page);
			assertThat(capturedPageable.getPageSize()).isEqualTo(size);
		}

	}

	@Nested
	@DisplayName("UUID로 멤버 가져오기")
	class getMemberByUuid {

		@Test
		@DisplayName("멤버가 존재하는 경우 멤버 가져오기에 성공한다")
		void should_successGetMember_when_memberExist() {
			//given
			Member member = memberTestHelper.makeRandomMember();
			given(memberRepository.findByUuid(member.getUuid())).willReturn(Optional.of(member));

			//when
			memberService.getMemberByUuid(member.getUuid());

			//then
			ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
			verify(memberRepository).findByUuid(stringArgumentCaptor.capture());
			String capturedString = stringArgumentCaptor.getValue();
			assertThat(capturedString).isEqualTo(member.getUuid());

			ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
			verify(modelMapper).map(memberArgumentCaptor.capture(), any(Class.class));
			Member capturedMember = memberArgumentCaptor.getValue();
			assertThat(capturedMember).isEqualTo(member);
		}

		@Test
		@DisplayName("멤버가 존재하지 않는 경우 에러를 반환한다")
		void should_errorGetMember_when_memberNotExist() {
			//given
			given(memberRepository.findByUuid(anyString())).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(() -> memberService.getMemberByUuid(UUID.randomUUID().toString()))
				.isInstanceOf(BusinessException.class);
		}

	}

	@Test
	@Disabled
	void putMemberByUuid() {
		//TODO 미구현
	}

	@Nested
	@DisplayName("UUID로 멤버 삭제하기")
	class deleteMemberByUuid {

		@Test
		@DisplayName("멤버가 존재하는 경우 멤버 삭제하기에 성공한다")
		void should_successDeleteMember_when_memberExist() {
			//given
			Member member = memberTestHelper.makeRandomMember();
			given(memberRepository.findByUuid(member.getUuid())).willReturn(Optional.of(member));

			//when
			memberService.deleteMemberByUuid(member.getUuid());

			//then
			ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
			verify(memberRepository).findByUuid(stringArgumentCaptor.capture());
			String capturedString = stringArgumentCaptor.getValue();
			assertThat(capturedString).isEqualTo(member.getUuid());

			ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
			verify(memberRepository).delete(memberArgumentCaptor.capture());
			Member capturedMember = memberArgumentCaptor.getValue();
			assertThat(capturedMember).isEqualTo(member);
		}

		@Test
		@DisplayName("멤버가 존재하지 않는 경우 에러를 반환한다")
		void should_errorDeleteMember_when_memberNotExist() {
			//given
			given(memberRepository.findByUuid(anyString())).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(() -> memberService.deleteMemberByUuid(UUID.randomUUID().toString()))
				.isInstanceOf(BusinessException.class);
			verify(memberRepository, never()).delete(any());
		}

	}

	@Nested
	@DisplayName("UUID로 멤버 상태 변경하기")
	class changeMemberStatusByUuid {

		@Test
		@DisplayName("멤버가 활성 상태인 경우 멤버 비활성에 성공한다")
		void should_inactivateMember_when_memberIsActive() {
			//given
			Member member = memberTestHelper.makeActiveMember();
			given(memberRepository.findByUuid(member.getUuid())).willReturn(Optional.of(member));

			//when
			memberService.changeMemberStatusByUuid(member.getUuid(), MemberStatus.INACTIVE);

			//then
			ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
			verify(memberRepository).save(memberArgumentCaptor.capture());
			Member capturedMember = memberArgumentCaptor.getValue();
			assertThat(capturedMember.getUuid()).isEqualTo(member.getUuid());
			assertThat(capturedMember.getMemberStatus()).isEqualTo(MemberStatus.INACTIVE);
		}

		@Test
		@DisplayName("멤버가 활성 상태인 경우에도 멤버 활성에 성공한다")
		void should_activateMember_when_memberIsActive() {
			//given
			Member member = memberTestHelper.makeActiveMember();
			given(memberRepository.findByUuid(member.getUuid())).willReturn(Optional.of(member));

			//when
			memberService.changeMemberStatusByUuid(member.getUuid(), MemberStatus.ACTIVE);

			//then
			ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
			verify(memberRepository).save(memberArgumentCaptor.capture());
			Member capturedMember = memberArgumentCaptor.getValue();
			assertThat(capturedMember.getUuid()).isEqualTo(member.getUuid());
			assertThat(capturedMember.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
		}

		@Test
		@DisplayName("멤버가 비활성 상태인 경우 멤버 활성에 성공한다")
		void should_activateMember_when_memberIsInactive() {
			//given
			Member member = memberTestHelper.makeInactiveMember();
			given(memberRepository.findByUuid(member.getUuid())).willReturn(Optional.of(member));

			//when
			memberService.changeMemberStatusByUuid(member.getUuid(), MemberStatus.ACTIVE);

			//then
			ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
			verify(memberRepository).save(memberArgumentCaptor.capture());
			Member capturedMember = memberArgumentCaptor.getValue();
			assertThat(capturedMember.getUuid()).isEqualTo(member.getUuid());
			assertThat(capturedMember.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
		}

		@Test
		@DisplayName("멤버가 비활성 상태인 경우에도 멤버 비활성에 성공한다")
		void should_inactivateMember_when_memberIsInactive() {
			//given
			Member member = memberTestHelper.makeInactiveMember();
			given(memberRepository.findByUuid(member.getUuid())).willReturn(Optional.of(member));

			//when
			memberService.changeMemberStatusByUuid(member.getUuid(), MemberStatus.INACTIVE);

			//then
			ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
			verify(memberRepository).save(memberArgumentCaptor.capture());
			Member capturedMember = memberArgumentCaptor.getValue();
			assertThat(capturedMember.getUuid()).isEqualTo(member.getUuid());
			assertThat(capturedMember.getMemberStatus()).isEqualTo(MemberStatus.INACTIVE);
		}

		@Test
		@DisplayName("멤버가 존재하지 않는 경우 에러를 반환한다")
		void should_errorInactivateMember_when_memberNotExist() {
			//given
			given(memberRepository.findByUuid(anyString())).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(
				() -> memberService.changeMemberStatusByUuid(UUID.randomUUID().toString(), MemberStatus.INACTIVE))
				.isInstanceOf(BusinessException.class);
		}

	}

	@Test
	@Disabled
	void getMemberByJwt() {
		//TODO 미구현
	}
}
