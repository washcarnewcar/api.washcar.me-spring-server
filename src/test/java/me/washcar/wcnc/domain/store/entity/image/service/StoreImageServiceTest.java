package me.washcar.wcnc.domain.store.entity.image.service;

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
import me.washcar.wcnc.domain.store.entity.image.StoreImageTestHelper;
import me.washcar.wcnc.domain.store.entity.image.dao.StoreImageRepository;
import me.washcar.wcnc.domain.store.entity.image.dto.request.ImageRequestDto;
import me.washcar.wcnc.global.error.BusinessException;
import me.washcar.wcnc.global.utility.AuthorizationHelper;

@ExtendWith(MockitoExtension.class)
class StoreImageServiceTest {

	@Mock
	private StoreRepository storeRepository;

	@Mock
	private StoreImageRepository storeImageRepository;

	@Mock
	private AuthorizationHelper authorizationHelper;

	private static StoreTestHelper storeTestHelper;

	private static StoreImageTestHelper storeImageTestHelper;

	private static StoreImageService storeImageService;

	@BeforeAll
	static void beforeAll() {
		storeTestHelper = new StoreTestHelper();
		storeImageTestHelper = new StoreImageTestHelper();
	}

	@BeforeEach
	void beforeEach() {
		storeImageService = new StoreImageService(storeRepository, storeImageRepository, authorizationHelper,
			new ModelMapper());
	}

	@Nested
	@DisplayName("세차장 이미지 생성 요청하기")
	class postImageBySlug {

		@Test
		@DisplayName("세차장이 존재하지 않는 경우 세차장 이미지 생성 요청에 실패한다")
		void should_failToPutStoreImage_when_storeNotExist() {
			//given
			ImageRequestDto imageRequestDto = storeImageTestHelper.makeStaticImageRequestDto();
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(() -> storeImageService.postImageBySlug("badStoreSlug", imageRequestDto))
				.isInstanceOf(BusinessException.class);
		}

		@Test
		@DisplayName("세차장의 이미지 최대 등록 가능 수를 초과하는 경우 세차장 이미지 생성 요청에 실패한다")
		void should_failToPutStoreImage_when_exceedStoreImageLimit() {
			//given
			ImageRequestDto imageRequestDto = storeImageTestHelper.makeStaticImageRequestDto();
			Store store = storeTestHelper.makeStaticRunningStore();
			given(storeRepository.findBySlug(anyString())).willReturn(Optional.of(store));

			//when & then
			for (int i = 0; i < 6; i++) {
				storeImageService.postImageBySlug(store.getSlug(), imageRequestDto);
			}
			assertThatThrownBy(() -> storeImageService.postImageBySlug(store.getSlug(), imageRequestDto))
				.isInstanceOf(BusinessException.class);
		}

	}

	@Nested
	@DisplayName("세차장 이미지 목록 가져오기")
	@Disabled
	class getImagesBySlug {
	}

	@Nested
	@DisplayName("개별 이미지 가져오기")
	@Disabled
	class getImageByUuid {
	}

	@Nested
	@DisplayName("이미지 삭제하기")
	@Disabled
	class deleteImageByUuid {
	}
}
