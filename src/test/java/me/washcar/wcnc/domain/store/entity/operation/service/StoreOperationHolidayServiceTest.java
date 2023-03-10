package me.washcar.wcnc.domain.store.entity.operation.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import me.washcar.wcnc.domain.store.StoreTestHelper;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.domain.store.entity.operation.StoreOperationHolidayTestHelper;
import me.washcar.wcnc.domain.store.entity.operation.dao.StoreOperationHolidayRepository;
import me.washcar.wcnc.domain.store.entity.operation.dto.request.HolidayRequestDto;
import me.washcar.wcnc.domain.store.service.StoreService;
import me.washcar.wcnc.global.error.BusinessException;

@ExtendWith(MockitoExtension.class)
class StoreOperationHolidayServiceTest {

	@Mock
	private StoreService storeService;

	@Mock
	private StoreRepository storeRepository;

	@Mock
	private StoreOperationHolidayRepository storeOperationHolidayRepository;

	private static StoreOperationHolidayTestHelper storeOperationHolidayTestHelper;

	private static StoreTestHelper storeTestHelper;

	private StoreOperationHolidayService storeOperationHolidayService;

	@BeforeAll
	static void beforeAll() {
		storeOperationHolidayTestHelper = new StoreOperationHolidayTestHelper();
		storeTestHelper = new StoreTestHelper();
	}

	@BeforeEach
	void beforeEach() {
		storeOperationHolidayService = new StoreOperationHolidayService(storeService,
			storeRepository, storeOperationHolidayRepository, new ModelMapper());
	}

	@Nested
	@DisplayName("세차장 휴일 생성하기")
	class create {

		@Test
		@DisplayName("휴일 시작 시간이 휴일 마감 시간 이후로 설정된 요청의 경우 세차장 휴일 생성 요청에 실패한다")
		void should_failToAddStoreOperationHoliday_when_startTimeIsAfterEndTime() {
			//given
			HolidayRequestDto requestDto = storeOperationHolidayTestHelper.makeBadOperationHolidayRequestDto();
			Store store = storeTestHelper.makeStaticRunningStore();
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));

			//when & then
			assertThatThrownBy(() -> storeOperationHolidayService.create("goodSlug", requestDto)).isInstanceOf(
				BusinessException.class);
		}

	}

}
