package me.washcar.wcnc.domain.store.entity.operation.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.washcar.wcnc.domain.store.StoreTestHelper;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.domain.store.entity.operation.StoreOperationHourTestHelper;
import me.washcar.wcnc.domain.store.entity.operation.dto.request.OperationHourRequestDto;
import me.washcar.wcnc.domain.store.entity.operation.dto.response.OperationHourDto;
import me.washcar.wcnc.domain.store.entity.operation.entity.OperationHour;
import me.washcar.wcnc.domain.store.service.StoreService;
import me.washcar.wcnc.global.error.BusinessException;

@ExtendWith(MockitoExtension.class)
class OperationHourServiceTest {

	@Mock
	private StoreService storeService;

	@Mock
	private StoreRepository storeRepository;

	private static StoreOperationHourTestHelper storeOperationHourTestHelper;

	private static StoreTestHelper storeTestHelper;

	private OperationHourService operationHourService;

	@BeforeAll
	static void beforeAll() {
		storeTestHelper = new StoreTestHelper();
		storeOperationHourTestHelper = new StoreOperationHourTestHelper();
	}

	@BeforeEach
	void beforeEach() {
		operationHourService = new OperationHourService(storeService, storeRepository);
	}

	@Nested
	@DisplayName("세차장 운영 시간 가져오기")
	class getOperationHourBySlug {

		@Test
		@DisplayName("세차장이 존재하는 경우 세차장 기본 운영 시간 가져오기에 성공한다")
		void should_successToGetStoreOperationHour_when_storeExist() {
			//given
			Store store = storeTestHelper.makeStaticRunningStore();
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));

			//when
			OperationHourDto operationHourDto = operationHourService.getOperationHourBySlug("goodSlug");

			//then
			LocalTime defaultBeginningTime = LocalTime.of(9, 0, 0);
			LocalTime defaultEndTime = LocalTime.of(18, 0, 0);

			assertThat(operationHourDto.getSundayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHourDto.getMondayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHourDto.getTuesdayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHourDto.getWednesdayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHourDto.getThursdayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHourDto.getFridayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHourDto.getSaturdayStartTime()).isEqualTo(defaultBeginningTime);

			assertThat(operationHourDto.getSundayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHourDto.getMondayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHourDto.getTuesdayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHourDto.getWednesdayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHourDto.getThursdayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHourDto.getFridayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHourDto.getSaturdayEndTime()).isEqualTo(defaultEndTime);
		}

	}

	@Nested
	@DisplayName("세차장 운영 시간 수정하기")
	class putOperationHourBySlug {

		@Test
		@DisplayName("세차장 주인인 경우 세차장 운영시간 변경에 성공한다")
		void should_successToPutStoreOperationHour_when_iAmOwner() {
			//given
			OperationHourRequestDto requestDto = storeOperationHourTestHelper.makeStaticOperationHourRequestDto();
			Store store = storeTestHelper.makeStaticRunningStore();
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));

			//when
			operationHourService.putOperationHourBySlug("goodSlug", requestDto);

			//then
			OperationHour operationHour = store.getOperationHour();
			assertThat(operationHour.getSundayStartTime()).isEqualTo(requestDto.getSundayStartTime());
			assertThat(operationHour.getMondayStartTime()).isEqualTo(requestDto.getMondayStartTime());
			assertThat(operationHour.getTuesdayStartTime()).isEqualTo(requestDto.getTuesdayStartTime());
			assertThat(operationHour.getWednesdayStartTime()).isEqualTo(requestDto.getWednesdayStartTime());
			assertThat(operationHour.getThursdayStartTime()).isEqualTo(requestDto.getThursdayStartTime());
			assertThat(operationHour.getFridayStartTime()).isEqualTo(requestDto.getFridayStartTime());
			assertThat(operationHour.getSaturdayEndTime()).isEqualTo(requestDto.getSaturdayEndTime());

			assertThat(operationHour.getSundayEndTime()).isEqualTo(requestDto.getSundayEndTime());
			assertThat(operationHour.getMondayEndTime()).isEqualTo(requestDto.getMondayEndTime());
			assertThat(operationHour.getTuesdayEndTime()).isEqualTo(requestDto.getTuesdayEndTime());
			assertThat(operationHour.getWednesdayEndTime()).isEqualTo(requestDto.getWednesdayEndTime());
			assertThat(operationHour.getThursdayEndTime()).isEqualTo(requestDto.getThursdayEndTime());
			assertThat(operationHour.getFridayEndTime()).isEqualTo(requestDto.getFridayEndTime());
			assertThat(operationHour.getSaturdayEndTime()).isEqualTo(requestDto.getSaturdayEndTime());
		}

		@Test
		@DisplayName("일반 유저인 경우 세차장 운영시간 변경에 실패한다")
		void should_failToPutStoreOperationHour_when_iAmUser() {
			//given
			OperationHourRequestDto requestDto = storeOperationHourTestHelper.makeStaticOperationHourRequestDto();
			Store store = storeTestHelper.makeStaticRunningStore();
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));
			doThrow(BusinessException.class).when(storeService).checkStoreChangePermit(store);

			//when & then
			assertThatThrownBy(
				() -> operationHourService.putOperationHourBySlug("goodSlug", requestDto)).isInstanceOf(
				BusinessException.class);

			OperationHour operationHour = store.getOperationHour();
			assertThat(operationHour.getSundayStartTime()).isNotEqualTo(requestDto.getSundayStartTime());
			assertThat(operationHour.getMondayStartTime()).isNotEqualTo(requestDto.getMondayStartTime());
			assertThat(operationHour.getTuesdayStartTime()).isNotEqualTo(requestDto.getTuesdayStartTime());
			assertThat(operationHour.getWednesdayStartTime()).isNotEqualTo(requestDto.getWednesdayStartTime());
			assertThat(operationHour.getThursdayStartTime()).isNotEqualTo(requestDto.getThursdayStartTime());
			assertThat(operationHour.getFridayStartTime()).isNotEqualTo(requestDto.getFridayStartTime());
			assertThat(operationHour.getSaturdayEndTime()).isNotEqualTo(requestDto.getSaturdayEndTime());

			assertThat(operationHour.getSundayEndTime()).isNotEqualTo(requestDto.getSundayEndTime());
			assertThat(operationHour.getMondayEndTime()).isNotEqualTo(requestDto.getMondayEndTime());
			assertThat(operationHour.getTuesdayEndTime()).isNotEqualTo(requestDto.getTuesdayEndTime());
			assertThat(operationHour.getWednesdayEndTime()).isNotEqualTo(requestDto.getWednesdayEndTime());
			assertThat(operationHour.getThursdayEndTime()).isNotEqualTo(requestDto.getThursdayEndTime());
			assertThat(operationHour.getFridayEndTime()).isNotEqualTo(requestDto.getFridayEndTime());
			assertThat(operationHour.getSaturdayEndTime()).isNotEqualTo(requestDto.getSaturdayEndTime());
		}

		@Test
		@DisplayName("운영 시작 시간이 운영 마감 시간 이후로 설정된 요청의 경우 세차장 운영시간 변경에 실패한다")
		void should_failToPutStoreOperationHour_when_startTimeIsAfterEndTime() {
			//given
			OperationHourRequestDto requestDto = storeOperationHourTestHelper.makeBadOperationHourRequestDto();
			Store store = storeTestHelper.makeStaticRunningStore();
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));

			//when & then
			assertThatThrownBy(
				() -> operationHourService.putOperationHourBySlug("goodSlug", requestDto)).isInstanceOf(
				BusinessException.class);

			OperationHour operationHour = store.getOperationHour();
			LocalTime defaultBeginningTime = LocalTime.of(9, 0, 0);
			LocalTime defaultEndTime = LocalTime.of(18, 0, 0);

			assertThat(operationHour.getSundayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHour.getMondayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHour.getTuesdayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHour.getWednesdayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHour.getThursdayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHour.getFridayStartTime()).isEqualTo(defaultBeginningTime);
			assertThat(operationHour.getSaturdayStartTime()).isEqualTo(defaultBeginningTime);

			assertThat(operationHour.getSundayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHour.getMondayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHour.getTuesdayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHour.getWednesdayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHour.getThursdayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHour.getFridayEndTime()).isEqualTo(defaultEndTime);
			assertThat(operationHour.getSaturdayEndTime()).isEqualTo(defaultEndTime);
		}

	}
}
