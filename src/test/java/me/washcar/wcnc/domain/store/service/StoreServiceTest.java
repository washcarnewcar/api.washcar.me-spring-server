package me.washcar.wcnc.domain.store.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
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

import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.store.StoreStatus;
import me.washcar.wcnc.domain.store.StoreTestHelper;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.dto.request.StoreRequestDto;
import me.washcar.wcnc.domain.store.dto.response.StoreDto;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.global.error.BusinessException;
import me.washcar.wcnc.global.utility.AuthorizationHelper;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

	@Mock
	private StoreRepository storeRepository;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private AuthorizationHelper authorizationHelper;

	private static StoreTestHelper storeTestHelper;

	private StoreService storeService;

	@BeforeAll
	static void beforeAll() {
		storeTestHelper = new StoreTestHelper();
	}

	@BeforeEach
	void beforeEach() {
		storeService = new StoreService(storeRepository, memberRepository, authorizationHelper, new ModelMapper());
	}

	@Nested
	@DisplayName("세차장 생성 요청하기")
	@Disabled
	class postStore {
	}

	@Nested
	@DisplayName("세차장 목록 가져오기")
	class getStoreList {

		@Test
		@DisplayName("page와 size가 정상 범위인 경우 Pageable역시 동일한 값을 가진다")
		void should_pageableIsCorrect_when_pageAndSizesAreInCorrectRange() {
			//given
			int page = 2;
			int size = 10;
			Pageable pageable = PageRequest.of(page, size);
			@SuppressWarnings("unchecked")
			Page<Store> storePage = Mockito.mock(Page.class);
			when(storeRepository.findAll(pageable)).thenReturn(storePage);

			//when
			storeService.getStoreList(page, size);

			//then
			ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
			verify(storeRepository).findAll(pageableArgumentCaptor.capture());
			Pageable capturedPageable = pageableArgumentCaptor.getValue();
			assertThat(capturedPageable.getPageNumber()).isEqualTo(page);
			assertThat(capturedPageable.getPageSize()).isEqualTo(size);
		}

	}

	@Nested
	@DisplayName("SLUG로 세차장 가져오기")
	class getStoreBySlug {

		@Test
		@DisplayName("세차장이 존재하지 않는 경우 세차장 가져오기에 실패한다")
		void should_failToGetStore_when_storeNotExist() {
			//given
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(() -> storeService.getStoreBySlug("badStoreSlug"))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("일반 유저는 세차장이 운영중인 경우 세차장 가져오기에 성공한다")
		void should_userSuccessToGetStore_when_storeIsRunning() {
			//given
			Store store = storeTestHelper.makeStaticRunningStore();
			Store spyStore = Mockito.spy(store);
			when(spyStore.isOwnedBy(any())).thenReturn(false);
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(spyStore));
			given(authorizationHelper.isManager()).willReturn(false);

			//when
			StoreDto result = storeService.getStoreBySlug(store.getSlug());

			//then
			assertThat(result.getSlug()).isEqualTo(store.getSlug());
			assertThat(result.getName()).isEqualTo(store.getName());
			assertThat(result.getLocation().getLongitude()).isEqualTo(store.getLocation().getLongitude());
		}

		@Test
		@DisplayName("일반 유저는 세차장이 휴점중인 경우 세차장 가져오기에 실패한다")
		void should_userFailsToGetStore_when_storeIsClosed() {
			//given
			Store store = storeTestHelper.makeStaticClosedStore();
			Store spyStore = Mockito.spy(store);
			when(spyStore.isOwnedBy(any())).thenReturn(false);
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(spyStore));
			given(authorizationHelper.isManager()).willReturn(false);

			//when & then
			assertThatThrownBy(() -> storeService.getStoreBySlug(store.getSlug()))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("일반 유저는 세차장이 대기중인 경우 세차장 가져오기에 실패한다")
		void should_userFailsToGetStore_when_storeIsPending() {
			//given
			Store store = storeTestHelper.makeStaticPendingStore();
			Store spyStore = Mockito.spy(store);
			when(spyStore.isOwnedBy(any())).thenReturn(false);
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(spyStore));
			given(authorizationHelper.isManager()).willReturn(false);

			//when & then
			assertThatThrownBy(() -> storeService.getStoreBySlug(store.getSlug()))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("일반 유저는 세차장이 거부 상태인 경우 세차장 가져오기에 실패한다")
		void should_userFailsToGetStore_when_storeIsRejected() {
			//given
			Store store = storeTestHelper.makeStaticRejectedStore();
			Store spyStore = Mockito.spy(store);
			when(spyStore.isOwnedBy(any())).thenReturn(false);
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(spyStore));
			given(authorizationHelper.isManager()).willReturn(false);

			//when & then
			assertThatThrownBy(() -> storeService.getStoreBySlug(store.getSlug()))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("관리자는 세차장이 운영중인 경우 세차장 가져오기에 성공한다")
		void should_managerSuccessToGetStore_when_storeIsRunning() {
			//given
			Store store = storeTestHelper.makeStaticRunningStore();
			Store spyStore = Mockito.spy(store);
			when(spyStore.isOwnedBy(any())).thenReturn(false);
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(spyStore));
			given(authorizationHelper.isManager()).willReturn(true);

			//when
			StoreDto result = storeService.getStoreBySlug(store.getSlug());

			//then
			assertThat(result.getSlug()).isEqualTo(store.getSlug());
			assertThat(result.getName()).isEqualTo(store.getName());
			assertThat(result.getLocation().getLongitude()).isEqualTo(store.getLocation().getLongitude());
		}

		@Test
		@DisplayName("관리자는 세차장이 운영중이 아닌 경우에도 세차장 가져오기에 성공한다")
		void should_managerSuccessToGetStore_when_storeIsNotRunning() {
			//given
			Store store = storeTestHelper.makeStaticClosedStore();
			Store spyStore = Mockito.spy(store);
			when(spyStore.isOwnedBy(any())).thenReturn(false);
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(spyStore));
			given(authorizationHelper.isManager()).willReturn(true);

			//when
			StoreDto result = storeService.getStoreBySlug(store.getSlug());

			//then
			assertThat(result.getSlug()).isEqualTo(store.getSlug());
			assertThat(result.getName()).isEqualTo(store.getName());
			assertThat(result.getLocation().getLongitude()).isEqualTo(store.getLocation().getLongitude());
		}

		@Test
		@DisplayName("세차장 주인은 내 세차장이 운영중이 아닌 경우에도 세차장 가져오기에 성공한다")
		void should_ownerSuccessToGetStore_when_myStoreIsNotRunning() {
			//given
			Store store = storeTestHelper.makeStaticClosedStore();
			Store spyStore = Mockito.spy(store);
			when(spyStore.isOwnedBy(any())).thenReturn(true);
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(spyStore));
			given(authorizationHelper.isManager()).willReturn(false);

			//when
			StoreDto result = storeService.getStoreBySlug(store.getSlug());

			//then
			assertThat(result.getSlug()).isEqualTo(store.getSlug());
			assertThat(result.getName()).isEqualTo(store.getName());
			assertThat(result.getLocation().getLongitude()).isEqualTo(store.getLocation().getLongitude());
		}

	}

	@Nested
	@DisplayName("세차장 정보 수정하기")
	class putStoreBySlug {

		@Test
		@DisplayName("일반 유저인 경우 세차장 정보 수정에 실패한다")
		void should_failsToPutStore_when_iAmUser() {
			//given
			StoreRequestDto storeRequestDto = storeTestHelper.makeStaticStoreRequestDto();
			Store store = storeTestHelper.makeStaticClosedStore();
			Store spyStore = Mockito.spy(store);
			when(spyStore.isOwnedBy(any())).thenReturn(false);
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(spyStore));
			given(authorizationHelper.isManager()).willReturn(false);

			//when & then
			assertThatThrownBy(() -> storeService.putStoreBySlug(store.getSlug(), storeRequestDto))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("세차장 주인인 경우 세차장 정보 수정에 성공한다")
		void should_successToPutStore_when_iAmOwner() {
			//given
			StoreRequestDto storeRequestDto = storeTestHelper.makeStaticStoreRequestDto();
			Store store = storeTestHelper.makeStaticClosedStore();
			Store spyStore = Mockito.spy(store);
			when(spyStore.isOwnedBy(any())).thenReturn(true);
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(spyStore));
			given(authorizationHelper.isManager()).willReturn(false);

			//when
			StoreDto result = storeService.putStoreBySlug(store.getSlug(), storeRequestDto);

			//then
			assertThat(result.getSlug()).isEqualTo(storeRequestDto.getSlug());
			assertThat(result.getName()).isEqualTo(storeRequestDto.getName());
			assertThat(result.getLocation().getLongitude()).isEqualTo(storeRequestDto.getLocation().getLongitude());
		}

		@Test
		@DisplayName("관리자인 경우 세차장 정보 수정에 성공한다")
		void should_successToPutStore_when_iAmManager() {
			//given
			StoreRequestDto storeRequestDto = storeTestHelper.makeStaticStoreRequestDto();
			Store store = storeTestHelper.makeStaticClosedStore();
			Store spyStore = Mockito.spy(store);
			when(spyStore.isOwnedBy(any())).thenReturn(false);
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(spyStore));
			given(authorizationHelper.isManager()).willReturn(true);

			//when
			StoreDto result = storeService.putStoreBySlug(store.getSlug(), storeRequestDto);

			//then
			assertThat(result.getSlug()).isEqualTo(storeRequestDto.getSlug());
			assertThat(result.getName()).isEqualTo(storeRequestDto.getName());
			assertThat(result.getLocation().getLongitude()).isEqualTo(storeRequestDto.getLocation().getLongitude());
		}
	}

	@Nested
	@DisplayName("세차장 삭제하기")
	class deleteStoreBySlug {

		@Test
		@DisplayName("세차장이 존재하는 경우 세차장 삭제하기에 성공한다")
		void should_successDeleteStore_when_storeExist() {
			//given
			Store store = storeTestHelper.makeStaticClosedStore();
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));

			//when
			storeService.deleteStoreBySlug(store.getSlug());

			//then
			ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
			verify(storeRepository).findBySlug(stringArgumentCaptor.capture());
			String capturedString = stringArgumentCaptor.getValue();
			assertThat(capturedString).isEqualTo(store.getSlug());

			ArgumentCaptor<Store> storeArgumentCaptor = ArgumentCaptor.forClass(Store.class);
			verify(storeRepository).delete(storeArgumentCaptor.capture());
			Store capturedStore = storeArgumentCaptor.getValue();
			assertThat(capturedStore).isEqualTo(store);
		}

		@Test
		@DisplayName("세차장이 존재하지 않는 경우 에러를 반환한다")
		void should_errorDeleteStore_when_storeNotExist() {
			//given
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(() -> storeService.deleteStoreBySlug("badStoreSlug"))
				.isInstanceOf(BusinessException.class);
			verify(storeRepository, never()).delete(any());
		}

	}

	@Nested
	@DisplayName("세차장 상태 변경하기")
	class changeStoreStatusBySlug {

		@Test
		@DisplayName("USER의 세차장이 승인되는 경우 세차장 주인의 권한이 OWNER로 변경된다")
		void should_userBecomeOwner_when_storeBecomeRunning() {
			//given
			Store store = storeTestHelper.makeStaticClosedStore();
			store.getOwner().demote(MemberRole.ROLE_USER);
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));

			//when
			storeService.changeStoreStatusBySlug(store.getSlug(), StoreStatus.RUNNING);

			//then
			assertThat(store.getStatus()).isEqualTo(StoreStatus.RUNNING);
			assertThat(store.getOwner().getMemberRole()).isEqualTo(MemberRole.ROLE_OWNER);
		}

		@Test
		@DisplayName("OWNER의 세차장이 승인되는 경우 세차장 주인의 권한이 OWNER로 유지된다")
		void should_ownerStaysOwner_when_storeBecomeRunning() {
			//given
			Store store = storeTestHelper.makeStaticClosedStore();
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));

			//when
			storeService.changeStoreStatusBySlug(store.getSlug(), StoreStatus.RUNNING);

			//then
			assertThat(store.getStatus()).isEqualTo(StoreStatus.RUNNING);
			assertThat(store.getOwner().getMemberRole()).isEqualTo(MemberRole.ROLE_OWNER);
		}

		@Test
		@DisplayName("ADMIN의 세차장이 승인되는 경우 세차장 주인의 권한이 ADMIN으로 유지된다")
		void should_adminStaysAdmin_when_storeBecomeRunning() {
			//given
			Store store = storeTestHelper.makeStaticClosedStore();
			store.getOwner().promote(MemberRole.ROLE_ADMIN);
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));

			//when
			storeService.changeStoreStatusBySlug(store.getSlug(), StoreStatus.RUNNING);

			//then
			assertThat(store.getStatus()).isEqualTo(StoreStatus.RUNNING);
			assertThat(store.getOwner().getMemberRole()).isEqualTo(MemberRole.ROLE_ADMIN);
		}

		@Test
		@DisplayName("SUPERMAN의 세차장이 승인되는 경우 세차장 주인의 권한이 SUPERMAN으로 유지된다")
		void should_supermanStaysSuperman_when_storeBecomeRunning() {
			//given
			Store store = storeTestHelper.makeStaticClosedStore();
			store.getOwner().promote(MemberRole.ROLE_SUPERMAN);
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));

			//when
			storeService.changeStoreStatusBySlug(store.getSlug(), StoreStatus.RUNNING);

			//then
			assertThat(store.getStatus()).isEqualTo(StoreStatus.RUNNING);
			assertThat(store.getOwner().getMemberRole()).isEqualTo(MemberRole.ROLE_SUPERMAN);
		}

		@Test
		@DisplayName("세차장이 존재하지 않는 경우 에러를 반환한다")
		void should_errorChangeStore_when_storeNotExist() {
			//given
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(() -> storeService.changeStoreStatusBySlug("badStoreSlug", StoreStatus.RUNNING))
				.isInstanceOf(BusinessException.class);
		}

	}

}
