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

import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.store.StoreTestHelper;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.dto.request.StoreRequestDto;
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

	@Mock
	private ModelMapper modelMapper;

	private static StoreTestHelper storeTestHelper;

	private static StoreService storeService;

	@BeforeAll
	static void beforeAll() {
		storeTestHelper = new StoreTestHelper();
	}

	@BeforeEach
	void beforeEach() {
		storeService = new StoreService(storeRepository, memberRepository, authorizationHelper, modelMapper);
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
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));
			given(authorizationHelper.isManager()).willReturn(false);
			StoreService spyStoreService = Mockito.spy(storeService);
			doReturn(false).when(spyStoreService).amIOwner(any());

			//when
			spyStoreService.getStoreBySlug(store.getSlug());

			//then
			ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
			verify(storeRepository).findBySlug(stringArgumentCaptor.capture());
			String capturedString = stringArgumentCaptor.getValue();
			assertThat(capturedString).isEqualTo(store.getSlug());
		}

		@Test
		@DisplayName("일반 유저는 세차장이 휴점중인 경우 세차장 가져오기에 실패한다")
		void should_userFailsToGetStore_when_storeIsClosed() {
			//given
			Store store = storeTestHelper.makeStaticClosedStore();
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));
			given(authorizationHelper.isManager()).willReturn(false);
			StoreService spyStoreService = Mockito.spy(storeService);
			doReturn(false).when(spyStoreService).amIOwner(any());

			//when & then
			assertThatThrownBy(() -> spyStoreService.getStoreBySlug(store.getSlug()))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("일반 유저는 세차장이 대기중인 경우 세차장 가져오기에 실패한다")
		void should_userFailsToGetStore_when_storeIsPending() {
			//given
			Store store = storeTestHelper.makeStaticPendingStore();
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));
			given(authorizationHelper.isManager()).willReturn(false);
			StoreService spyStoreService = Mockito.spy(storeService);
			doReturn(false).when(spyStoreService).amIOwner(any());

			//when & then
			assertThatThrownBy(() -> spyStoreService.getStoreBySlug(store.getSlug()))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("일반 유저는 세차장이 거부 상태인 경우 세차장 가져오기에 실패한다")
		void should_userFailsToGetStore_when_storeIsRejected() {
			//given
			Store store = storeTestHelper.makeStaticRejectedStore();
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));
			given(authorizationHelper.isManager()).willReturn(false);
			StoreService spyStoreService = Mockito.spy(storeService);
			doReturn(false).when(spyStoreService).amIOwner(any());

			//when & then
			assertThatThrownBy(() -> spyStoreService.getStoreBySlug(store.getSlug()))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("관리자는 세차장이 운영중인 경우 세차장 가져오기에 성공한다")
		void should_managerSuccessToGetStore_when_storeIsRunning() {
			//given
			Store store = storeTestHelper.makeStaticRunningStore();
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));
			given(authorizationHelper.isManager()).willReturn(true);
			StoreService spyStoreService = Mockito.spy(storeService);
			doReturn(false).when(spyStoreService).amIOwner(any());

			//when
			spyStoreService.getStoreBySlug(store.getSlug());

			//then
			ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
			verify(storeRepository).findBySlug(stringArgumentCaptor.capture());
			String capturedString = stringArgumentCaptor.getValue();
			assertThat(capturedString).isEqualTo(store.getSlug());
		}

		@Test
		@DisplayName("관리자는 세차장이 운영중이 아닌 경우에도 세차장 가져오기에 성공한다")
		void should_managerSuccessToGetStore_when_storeIsNotRunning() {
			//given
			Store store = storeTestHelper.makeStaticPendingStore();
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));
			given(authorizationHelper.isManager()).willReturn(true);
			StoreService spyStoreService = Mockito.spy(storeService);
			doReturn(false).when(spyStoreService).amIOwner(any());

			//when
			spyStoreService.getStoreBySlug(store.getSlug());

			//then
			ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
			verify(storeRepository).findBySlug(stringArgumentCaptor.capture());
			String capturedString = stringArgumentCaptor.getValue();
			assertThat(capturedString).isEqualTo(store.getSlug());
		}

		@Test
		@DisplayName("세차장 주인은 내 세차장이 운영중이 아닌 경우에도 세차장 가져오기에 성공한다")
		void should_ownerSuccessToGetStore_when_myStoreIsNotRunning() {
			//given
			Store store = storeTestHelper.makeStaticPendingStore();
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));
			given(authorizationHelper.isManager()).willReturn(false);
			StoreService spyStoreService = Mockito.spy(storeService);
			doReturn(true).when(spyStoreService).amIOwner(any());

			//when
			spyStoreService.getStoreBySlug(store.getSlug());

			//then
			ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
			verify(storeRepository).findBySlug(stringArgumentCaptor.capture());
			String capturedString = stringArgumentCaptor.getValue();
			assertThat(capturedString).isEqualTo(store.getSlug());
		}

	}

	@Nested
	@DisplayName("세차장 정보 수정하기")
	class putStoreBySlug {

		@Test
		@DisplayName("일반 유저인 경우 세차장 정보 수정에 실패한다")
		void should_failsToPutStore_when_iAmUser() {
			//given
			Store store = storeTestHelper.makeStaticRunningStore();
			StoreRequestDto storeRequestDto = storeTestHelper.makeStaticStoreRequestDto();
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));
			given(authorizationHelper.isManager()).willReturn(false);
			StoreService spyStoreService = Mockito.spy(storeService);
			doReturn(false).when(spyStoreService).amIOwner(any());

			//when & then
			assertThatThrownBy(() -> spyStoreService.putStoreBySlug(store.getSlug(), storeRequestDto))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("세차장 주인인 경우 세차장 정보 수정에 성공한다")
		void should_successToPutStore_when_iAmOwner() {
			//given
			Store store = storeTestHelper.makeStaticRunningStore();
			StoreRequestDto storeRequestDto = storeTestHelper.makeStaticStoreRequestDto();
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));
			given(authorizationHelper.isManager()).willReturn(false);
			StoreService spyStoreService = Mockito.spy(storeService);
			doReturn(true).when(spyStoreService).amIOwner(any());

			//when
			spyStoreService.putStoreBySlug(store.getSlug(), storeRequestDto);

			//then
			ArgumentCaptor<Store> storeArgumentCaptor = ArgumentCaptor.forClass(Store.class);
			verify(modelMapper).map(storeArgumentCaptor.capture(), any(Class.class));
			Store capturedStore = storeArgumentCaptor.getValue();
			assertThat(capturedStore.getName()).isEqualTo(storeRequestDto.getName());
			assertThat(capturedStore.getSlug()).isEqualTo(storeRequestDto.getSlug());
			assertThat(capturedStore.getLocation().getLongitude()).isEqualTo(
				storeRequestDto.getLocation().getLongitude());
		}

		@Test
		@DisplayName("관리자인 경우 세차장 정보 수정에 성공한다")
		void should_successToPutStore_when_iAmManager() {
			//given
			Store store = storeTestHelper.makeStaticRunningStore();
			StoreRequestDto storeRequestDto = storeTestHelper.makeStaticStoreRequestDto();
			given(storeRepository.findBySlug(store.getSlug())).willReturn(Optional.of(store));
			given(authorizationHelper.isManager()).willReturn(true);
			StoreService spyStoreService = Mockito.spy(storeService);
			doReturn(false).when(spyStoreService).amIOwner(any());

			//when
			spyStoreService.putStoreBySlug(store.getSlug(), storeRequestDto);

			//then
			ArgumentCaptor<Store> storeArgumentCaptor = ArgumentCaptor.forClass(Store.class);
			verify(modelMapper).map(storeArgumentCaptor.capture(), any(Class.class));
			Store capturedStore = storeArgumentCaptor.getValue();
			assertThat(capturedStore.getName()).isEqualTo(storeRequestDto.getName());
			assertThat(capturedStore.getSlug()).isEqualTo(storeRequestDto.getSlug());
			assertThat(capturedStore.getLocation().getLongitude()).isEqualTo(
				storeRequestDto.getLocation().getLongitude());
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
	@Disabled
	class changeStoreStatusBySlug {
	}

}
