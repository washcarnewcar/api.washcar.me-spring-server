package me.washcar.wcnc.domain.store.entity.menu.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import me.washcar.wcnc.domain.store.entity.menu.StoreMenuTestHelper;
import me.washcar.wcnc.domain.store.entity.menu.dao.MenuRepository;
import me.washcar.wcnc.domain.store.entity.menu.dto.request.MenuRequestDto;
import me.washcar.wcnc.domain.store.service.StoreService;
import me.washcar.wcnc.global.error.BusinessException;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

	@Mock
	private StoreService storeService;

	@Mock
	private StoreRepository storeRepository;

	@Mock
	private MenuRepository menuRepository;

	private static StoreTestHelper storeTestHelper;

	private static StoreMenuTestHelper storeMenuTestHelper;

	private MenuService menuService;

	@BeforeAll
	static void beforeAll() {
		storeTestHelper = new StoreTestHelper();
		storeMenuTestHelper = new StoreMenuTestHelper();
	}

	@BeforeEach
	void beforeEach() {
		menuService = new MenuService(storeService, storeRepository, menuRepository,
			new ModelMapper());
	}

	@Nested
	@DisplayName("세차장 메뉴 생성 요청하기")
	class create {

		@Test
		@DisplayName("세차장이 존재하지 않는 경우 세차장 메뉴 생성 요청에 실패한다")
		void should_failToPutStoreMenu_when_storeNotExist() {
			//given
			MenuRequestDto menuRequestDto = storeMenuTestHelper.makeStaticMenuRequestDto();
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(() -> menuService.create("badStoreSlug", menuRequestDto))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("세차장의 메뉴 최대 등록 가능 수를 초과하는 경우 세차장 메뉴 생성 요청에 실패한다")
		void should_failToPutStoreMenu_when_exceedStoreImageLimit() {
			//given
			MenuRequestDto menuRequestDto = storeMenuTestHelper.makeStaticMenuRequestDto();
			Store store = storeTestHelper.makeStaticRunningStore();
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));

			//when & then
			for (int i = 0; i < 64; i++) {
				menuService.create("goodStoreSlug", menuRequestDto);
			}
			assertThatThrownBy(() -> menuService.create("goodStoreSlug", menuRequestDto))
				.isInstanceOf(BusinessException.class);
		}

	}

	@Nested
	@DisplayName("세차장 메뉴 목록 가져오기")
	@Disabled
	class getMenusBySlug {
	}

	@Nested
	@DisplayName("개별 메뉴 가져오기")
	@Disabled
	class getMenuByUuid {
	}

	@Nested
	@DisplayName("메뉴 수정하기")
	@Disabled
	class putMenuByUuid {
	}

	@Nested
	@DisplayName("메뉴 삭제하기")
	@Disabled
	class deleteMenuByUuid {
	}
}
